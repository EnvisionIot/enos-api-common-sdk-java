package com.envisioniot.enos.enosapi.common.resource;

import com.envisioniot.enos.enosapi.common.enumeration.TransferType;

import java.io.File;

/**
 * created by dongdong.wang
 */
public class EnOSTransferFileInfo {

    private String transferName;
    private TransferType transferType;
    private File file;//当TransferType=UPLOAD时，表示待上传的文件信息；当TransferType=DOWNLOAD时，表示将下载到的地址信息
    private String content;

    public EnOSTransferFileInfo() {
    }

    public EnOSTransferFileInfo(String transferName, TransferType transferType, File file, String content) {
        this.transferName = transferName;
        this.transferType = transferType;
        this.file = file;
        this.content = content;
    }

    public EnOSTransferFileInfo(String transferName, TransferType transferType, File file) {
        this(transferName, transferType, file, null);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTransferName() {
        return transferName;
    }

    public void setTransferName(String transferName) {
        this.transferName = transferName;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public TransferType getTransferType() {
        return transferType;
    }

    public void setTransferType(TransferType transferType) {
        this.transferType = transferType;
    }
}
