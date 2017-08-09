package com.neusoft.sample.Ctrl.yangkangkang;

public class RiJiYueLeiStudy implements Comparable<RiJiYueLeiStudy>{    // 日积月累学习
    private String content;
    private String isAudio;
    private String isImg;
    private String isVideo;
    private String itemNo;
    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public void setIsAudio(String isAudio) {
        this.isAudio = isAudio;
    }

    public String getIsAudio() {
        return this.isAudio;
    }

    public void setIsImg(String isImg) {
        this.isImg = isImg;
    }

    public String getIsImg() {
        return this.isImg;
    }

    public void setIsVideo(String isVideo) {
        this.isVideo = isVideo;
    }

    public String getIsVideo() {
        return this.isVideo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public String getItemNo() {
        return this.itemNo;
    }

    @Override
    public int compareTo(RiJiYueLeiStudy another) {

        //从下表为7开始往后
//        if (Integer.valueOf(itemNo.substring(10))<Integer.valueOf(another.itemNo.substring(10)))return -1;
//        else if (Integer.valueOf(itemNo.substring(10))==Integer.valueOf(another.itemNo.substring(10)))
//            return 0;
        return itemNo.compareTo(another.getItemNo());
    }
}