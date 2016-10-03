package ru.levelp.model.utils;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class FileCompressor {

    public void zip(String sourcePath, String targetPath) throws IOException {
        Path path = FileSystems.getDefault().getPath(sourcePath);
        ArrayList<File> filesToCompress = new ArrayList<>();
        doFileList(path.toFile(), filesToCompress, 0);
        File target = new File(targetPath);
        target.createNewFile();
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(targetPath));

        for (File f : filesToCompress) {
            out.putNextEntry(new ZipEntry(f.getPath())); //file.getName()
            FileInputStream fis = new FileInputStream(f);
            write(fis, out);
        }
        out.close();
    }

    private ArrayList<File> doFileList(File file, ArrayList<File> filesToCompress, int depth) {
//        System.out.println(depth + " " + file.getName());
        if (file.isFile()) {
            filesToCompress.add(file);
        } else {
            for (File f : file.listFiles()) {
                doFileList(f, filesToCompress, depth + 1);
            }
        }
        return filesToCompress;
    }

    public void unzip(String sourceZip, String targetFolder) throws IOException {
        ZipFile zipFile = new ZipFile(sourceZip);
        Enumeration entries = zipFile.entries();

        while (entries.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            if (entry.isDirectory()) {
                File file = new File(targetFolder, entry.getName());
                file.mkdirs();
            } else {
                File file = new File(targetFolder, entry.getName());
                file.getParentFile().mkdirs();
                InputStream inputStream = zipFile.getInputStream(entry);
                file.createNewFile();
                FileOutputStream outputStream = new FileOutputStream(file);
                write(inputStream, outputStream);
                outputStream.close();
            }
        }
        zipFile.close();
    }

    private void write(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[2048];
        int len;
        while ((len = input.read(buffer)) >= 0) {
            output.write(buffer, 0, len);
        }
        input.close();
    }
}