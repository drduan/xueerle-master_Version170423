package com.neusoft.sample.Ctrl.wenchengcheng;


import java.util.Date;

/**
 * 系统检测更新的实体
 */
public class CheckForUpdate {

	private String 		name;			//应用名
	private String 		version;					//最新主版本号
	private String 		changelog;						//更新日志
	private Date 		updated_at;			//更新时间
	private String 		versionShort;		//最新全版本号
	private String 		installUrl;						//下载地址1
	private String 		install_url;						//下载地址2
	private String 		direct_install_url;						//直接安装地址
	private String 		update_url;						//应用发布页(FIR.IM)
	private String 		binary;						//应用大小


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getChangelog() {
		return changelog;
	}

	public void setChangelog(String changelog) {
		this.changelog = changelog;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}

	public String getVersionShort() {
		return versionShort;
	}

	public void setVersionShort(String versionShort) {
		this.versionShort = versionShort;
	}

	public String getInstallUrl() {
		return installUrl;
	}

	public void setInstallUrl(String installUrl) {
		this.installUrl = installUrl;
	}

	public String getInstall_url() {
		return install_url;
	}

	public void setInstall_url(String install_url) {
		this.install_url = install_url;
	}

	public String getDirect_install_url() {
		return direct_install_url;
	}

	public void setDirect_install_url(String direct_install_url) {
		this.direct_install_url = direct_install_url;
	}

	public String getUpdate_url() {
		return update_url;
	}

	public void setUpdate_url(String update_url) {
		this.update_url = update_url;
	}

	public String getBinary() {
		return binary;
	}

	public void setBinary(String binary) {
		this.binary = binary;
	}
}
