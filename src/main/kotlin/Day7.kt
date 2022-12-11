import java.awt.BufferCapabilities.FlipContents
import java.io.File
import java.lang.IllegalStateException

class Day7(val inputFile: File) {

    enum class Program {
        CHANGE_DIR,
        LIST_CONTENTS
    }

    data class Contents(val name: String, val fileSize: Long?, val isDir: Boolean) {
        companion object {

            private val matcher = Regex("""^(dir|\d+)""")

            fun parseContents(contentString: String): Contents? {
                val trimmed = contentString.trim()
                if (matcher.find(trimmed) == null) {
                    return null
                }
                return if (trimmed.startsWith("dir")) parseDir(trimmed) else parseFile(trimmed)
            }

            private fun parseFile(fileString: String): Contents {
                val (fileSizeString, fileName) = fileString.split(" ")
                return Contents(name = fileName, fileSize = fileSizeString.toLong(), isDir = false)
            }

            private fun parseDir(dirString: String) =
                Contents(dirString.replace("dir ", ""), fileSize = null, isDir = true)
        }
    }

    data class Command(val program: Program, val args: List<String>) {
        companion object {
            private val commandRegex: Regex = Regex("""\$\s+(?<cmd>\w+)(\s+(\S+))*""")

            fun parseCommand(toTest: String): Command? {
                return commandRegex.find(toTest)?.let { matchResult ->
                    when (matchResult.groups["cmd"]?.value) {
                        "cd" -> Command(
                            Program.CHANGE_DIR,
                            matchResult.groups.drop(2).mapNotNull { group -> group?.value })

                        "ls" -> Command(Program.LIST_CONTENTS, emptyList())
                        else -> null
                    }
                }
            }
        }
    }

    data class FileRef(
        val path: String,
        val isDir: Boolean,
        val parent: FileRef? = null,
        val fileSize: Long? = null
    ) {

        private val childDirectories: MutableMap<String, FileRef> = mutableMapOf()
        val files: MutableList<FileRef> = mutableListOf()
        val dirs get() = childDirectories.values.toList()

        fun addOrGetChildDir(name: String): FileRef =
            childDirectories.getOrPut(name) {
                FileRef(path = name, isDir = true, parent = this, fileSize = null)
            }

        fun addFile(name: String, fileSize: Long): FileRef {
            val file = FileRef(path = name, isDir = false, parent = this, fileSize = fileSize)
            files += file
            return file
        }

        val size: Long get() = fileSize ?: files.sumOf { f -> f.fileSize ?: 0 } + childDirectories.values.sumOf { d -> d.size }

    }

    fun deleteEnoughSpace(): Long {
        val totalDiskSpace = 70000000L
        val requiredDiskSpace = 30000000L
        val root = computeHierarchy()
        return getDirectoriesBiggerThan(requiredDiskSpace - (totalDiskSpace - root.size), root, listOf()).sortedBy { it.size }.first().size
    }

    fun sumAllSmallDirs(): Long {
        val root = computeHierarchy()
        return getDirectoriesSmallerThan(100001L, root, listOf()).sumOf { it.size }
    }

    private fun getDirectoriesBiggerThan(minSize: Long, ref: FileRef, acc: List<FileRef>): List<FileRef> {
        var nextAcc = if (ref.size > minSize) acc + ref else acc
        for (childDir in ref.dirs) {
            nextAcc = getDirectoriesBiggerThan(minSize, childDir, nextAcc)
        }
        return nextAcc
    }

    private fun getDirectoriesSmallerThan(maxSize: Long, ref: FileRef, acc: List<FileRef>): List<FileRef> {
        var nextAcc = if (ref.size < maxSize) acc + ref else acc
        for (childDir in ref.dirs) {
            nextAcc = getDirectoriesSmallerThan(maxSize, childDir, nextAcc)
        }
        return nextAcc
    }

    private fun computeHierarchy(): FileRef {
        var currentDir: FileRef? = null
        inputFile.readLines().toMutableList().let {
            val line = it.removeAt(0)
            var cmd = Command.parseCommand(line)
            while (cmd != null) {
                when (cmd.program) {
                    Program.CHANGE_DIR -> {
                        currentDir = changeDir(currentDir, cmd.args.first())
                    }
                    Program.LIST_CONTENTS -> listContents(currentDir!!, it)
                }
                cmd = if (it.size > 0) Command.parseCommand(it.removeAt(0)) else null
            }
        }
        return getRoot(currentDir ?: throw IllegalStateException("no directories found"))
    }

    private fun getRoot(file: FileRef): FileRef {
        return if (file.parent == null) file else getRoot(file.parent)
    }

    private fun listContents(currentDir: FileRef, lines: MutableList<String>) {
        var line = lines.removeAt(0)
        var contents = Contents.parseContents(line)
        while (contents != null) {
            if (contents.isDir) {
                currentDir.addOrGetChildDir(contents.name)
            } else {
                currentDir.addFile(contents.name, contents.fileSize ?: 0)
            }
            if (lines.size == 0) {
                return;
            }
            line = lines.removeAt(0)
            contents = Contents.parseContents(line)
            if (contents == null) {
                lines.add(0, line)
            }
        }
    }

    private fun changeDir(currentDir: FileRef?, target: String): FileRef  =
        when (val trimmed = target.trim()) {
            ".." -> currentDir?.parent ?: throw IllegalStateException("no parent directory to move to")
            "." -> currentDir ?: throw IllegalStateException("trying to change to cwd when no cwd exists")
            else -> changeDirToTarget(currentDir, trimmed)
        }

    private fun changeDirToTarget(currentDir: FileRef?, target: String): FileRef {
        if (target.startsWith("/")) {
            return FileRef(target, true, null, null)
        } else if (currentDir != null) {
            return currentDir.addOrGetChildDir(target)
        } else {
            return FileRef(target, true, null, null)
        }
    }
}