package com.yinhai.medicalbenefit.common.action;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.yinhai.sysframework.exception.AppException;
import com.yinhai.webframework.BaseAction;

/**

 */
public class ImageuploadAction extends BaseAction {

	private static final String UPLOAD_ROOT_FOLDER ="D:/image";
	private File file[];
	// 上传文件名
	private String fileFileName[];

	public File[] getFile() {
		return file;
	}

	public void setFile(File[] file) {
		this.file = file;
	}

	public String[] getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String[] fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String execute() {
		return SUCCESS;
	}

	public String upload() throws Exception {
		String registername=getDto().getAsString("regno");
		String rootURL ="D:/image/"+getDto().getUserInfo().getDepartment().getUserDefineDepartId()+"/"+registername;		
		try {
			
			// 获取文件夹的真实路径
			//String rootURL = ServletActionContext.getServletContext()
			//		.getRealPath(UPLOAD_ROOT_FOLDER);
			// 检查文件夹是否存在
			checkFolder(rootURL);
			for (int i = 0; i < file.length; i++) {
				// 获取上传文件的文件名
				String fileNameItem = fileFileName[i];

				String fileURL = rootURL + "\\" + fileNameItem;
				File newFile = new File(fileURL);
				// 将文件上传至服务器
				writeFile(file[i], newFile);
			}
		} catch (Exception e) {
			setMsg("上传失败");
			return FILE;
		}
		setMsg("上传成功，你已经成功上传了" + file.length + "个文件");
		return FILE;
	}

	/**
	 * 检查上传文件夹是否存在，不存在则创建
	 */
	private void checkFolder(String url) {
		File file = new File(url);
		if (!file.isFile() && !file.exists()) {
			file.mkdir();
		}
	}

	/**
	 * 将文件写入指定路径中
	 * 
	 * @param src源文件
	 * dst 目标文件
	 */
	private void writeFile(File src, File dst) {

		try {
			FileUtils.copyFile(src, dst);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
                                                                                                                                                                                                        