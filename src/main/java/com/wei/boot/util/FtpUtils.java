package com.wei.boot.util;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;

/**
 * wangxiantang06495hellobike.com
 * 2019/7/23:3:19 PM
 */
public class FtpUtils {

    private static final String host = "32.18.108.12";

    private static final Integer port = 22;

    private static final String userName = "Hellobike";

    private static final String auth = "Hellobike14%*!";

    /**
     * 初始化ftp服务器
     */
    private static FTPClient init() {
        FTPClient ftpClient = new FTPClient();
        ftpClient.setControlEncoding("utf-8");
        try {
            ftpClient.connect(host, port);
            ftpClient.login(userName, auth);
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ftpClient;
    }

    /**
     * 上传单个文件
     * @param path 存入ftp的路径
     * @param file 本地文件
     * @return
     */
    public static boolean uploadSingleFile(String path, File file) {
        FTPClient ftpClient = init();
        InputStream inputStream = null;
        boolean storeFlag = false;
        try {
            //切换目录，目录不存在创建目录
            boolean changeDirFlag=ftpClient.changeWorkingDirectory(path);
            if(!changeDirFlag){
                ftpClient.makeDirectory(path);
                ftpClient.changeWorkingDirectory(path);
            }
            inputStream = new FileInputStream(file);
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            storeFlag = ftpClient.storeFile(file.getName(), inputStream);
            if (!storeFlag) {
                System.out.println("上传失败");
            }
            inputStream.close();
            ftpClient.logout();
        } catch (Exception e) {
            System.out.println("上传失败");
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return storeFlag;
    }

    /**
     * 上传多个文件
     * @param path
     * @param files
     * @return
     */
    public static void uploadMultiFiles(String path, File[] files) {
        FTPClient ftpClient = init();
        try {
            //切换目录，目录不存在创建目录
            boolean changeDirFlag=ftpClient.changeWorkingDirectory(path);
            if(!changeDirFlag){
                ftpClient.makeDirectory(path);
                ftpClient.changeWorkingDirectory(path);
            }
            if(files.length > 0){
                for(File file : files){
                    String fileName = file.getName();
                    InputStream inputStream = null;
                    try {
                        inputStream = new FileInputStream(file);
                        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                        ftpClient.enterLocalPassiveMode();
                        boolean storeFlag = ftpClient.storeFile(fileName, inputStream);
                        if(storeFlag){
                            System.out.println("文件发送成功！开始删除文件...");
                            file.delete();
                        } else{
                            // 上传失败的文件重命名做后续处理
                            fileName = fileName + ".failed";
                            //file.renameTo(new File(CommonConstant.WA_UPLOAD_FILE_FOLDER+ fileName));
                        }
                    } finally {
                        if (null != inputStream) {
                            try {
                                inputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            ftpClient.logout();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static File[] listFailedFiles(String folderName){
        File folder = new File(folderName);
        File[] fileArray = folder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if(name.endsWith(".xml")){
                    return true;
                }
                return false;
            }
        });
        return fileArray;
    }
}
