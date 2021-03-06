package org.fabiomsr.drawableoptimizer.util

import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.Project

/**
 * Created by fabiomsr on 8/5/16.
 */
class ZopfliFileSystemUtils {

    static def getZopfliDirectory(Project project){
        return new File(getZopfliDirectoryPath(project))
    }

    static def getZopfliFilename(Project project){
        return Os.isFamily(Os.FAMILY_WINDOWS) ? 'zopflipng.exe' : 'zopflipng'
    }

    static def getZopfliFilePath(Project project){
        return getZopfliDirectoryPath(project) + File.separator + getZopfliFilename(project)
    }

    private static def getZopfliDirectoryPath(Project project){
        return project.buildDir.absolutePath + File.separator + 'zopfli'
    }

    static def copyZopfliToBuildFolder(Project project) {
        def zopfliFileDir = getZopfliDirectory(project)
        if(!zopfliFileDir.exists()) {
            zopfliFileDir.mkdirs()

            def zopfliFilename = getZopfliFilename(project)
            def zopfliFile = new File(getZopfliFilePath(project))

            new FileOutputStream(zopfliFile).withStream {
                def zopfliResource = "/zopfli/$zopfliFilename"
                def stream = ZopfliFileSystemUtils.class.getResourceAsStream(zopfliResource)
                it.write(stream.getBytes())
            }

            zopfliFile.setExecutable(true)
        }
    }
}
