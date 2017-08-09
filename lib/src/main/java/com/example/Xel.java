package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class Xel {
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(2, "com.neusoft.sample.GreenDao");
        addNote(schema);
//        new DaoGenerator().generateAll(schema,
//                "/Users/Administrator/Desktop/xel/xueerle-master/app/src/main/java-gen");
        new DaoGenerator().generateAll(schema,
                "C:\\Users\\Administrator\\Desktop\\xueerle-master_Version1222\\app\\src\\main\\java-gen");
    }

    /**
     * @param schema
     */
    private static void addNote(Schema schema) {
        Entity note = schema.addEntity("User");
        note.addIdProperty().autoincrement();
        note.addStringProperty("phone").notNull().unique();
        note.addStringProperty("psword").notNull();
        note.addStringProperty("role").notNull();
        note.addStringProperty("server_id");
        note.addStringProperty("province");
        note.addStringProperty("province_nub");
        note.addStringProperty("city");
        note.addStringProperty("city_nub");
        note.addStringProperty("region");
        note.addStringProperty("region_nub");
        note.addStringProperty("school");
        note.addStringProperty("school_nub");
        note.addStringProperty("grade");
        note.addStringProperty("grade_nub");
        note.addStringProperty("classes");
        note.addStringProperty("classes_nub");
        note.addStringProperty("productNo");

        note.addStringProperty("user_icon_url");
        note.addStringProperty("gender");
        note.addStringProperty("qq_Number");
        note.addStringProperty("Weixin_number");
        note.addStringProperty("Motto");
        note.addStringProperty("Recipient");
        note.addStringProperty("Address");
        note.addStringProperty("Zip_code");
        note.addStringProperty("email");
        note.addStringProperty("mobile");

        Entity TextOneStructure = schema.addEntity("TextOneStructure");
        TextOneStructure.addIdProperty().autoincrement();
        TextOneStructure.addStringProperty("chapterNo").notNull().unique();
        TextOneStructure.addIntProperty("showSequence");
        TextOneStructure.addIntProperty("chapterSequence");
        TextOneStructure.addStringProperty("chapterSequenceName");
        TextOneStructure.addStringProperty("chapterName");
        TextOneStructure.addStringProperty("hasSectionNum");
        TextOneStructure.addBooleanProperty("hasResource");
        TextOneStructure.addBooleanProperty("FullFree");

        Entity TextTwoStructure = schema.addEntity("TextTwoStructure");
        TextTwoStructure.addIdProperty().autoincrement();
        TextTwoStructure.addStringProperty("sectionNo").notNull().unique();
        TextTwoStructure.addIntProperty("showSequence");
        TextTwoStructure.addIntProperty("sectionSequence");
        TextTwoStructure.addStringProperty("sectionSequenceName");
        TextTwoStructure.addStringProperty("sectionName");
        TextTwoStructure.addBooleanProperty("hasResource");
        TextTwoStructure.addBooleanProperty("Free");
        TextTwoStructure.addStringProperty("chapterNo");
        TextTwoStructure.addStringProperty("kpNo");

        Entity StudyGoodTerm = schema.addEntity("StudyGoodTerm");
        StudyGoodTerm.addIdProperty().autoincrement();
        StudyGoodTerm.addStringProperty("bookNo").notNull().unique();
        StudyGoodTerm.addStringProperty("name");
        StudyGoodTerm.addStringProperty("grade");
        StudyGoodTerm.addIntProperty("term");
        StudyGoodTerm.addIntProperty("type");
        StudyGoodTerm.addBooleanProperty("hasIntroducePDF");
        StudyGoodTerm.addIntProperty("chapterNum");
        StudyGoodTerm.addStringProperty("version");
        StudyGoodTerm.addIntProperty("hasCoverPIC");
        StudyGoodTerm.addDateProperty("updateTime");

        Entity StudyGoodInfo = schema.addEntity("StudyGoodInfo");
        StudyGoodInfo.addIdProperty().autoincrement();
        StudyGoodInfo.addStringProperty("productNo").notNull().unique();
        StudyGoodInfo.addStringProperty("bookGroupNo");
        StudyGoodInfo.addStringProperty("introduce");
        StudyGoodInfo.addStringProperty("price");
        StudyGoodInfo.addStringProperty("overdue");
        StudyGoodInfo.addStringProperty("priceOnSale");
        StudyGoodInfo.addStringProperty("dueDate");

        Entity StudyGoodItem = schema.addEntity("StudyGoodItem");
        StudyGoodItem.addIdProperty().autoincrement();
        StudyGoodItem.addStringProperty("productItemNo").notNull().unique();
        StudyGoodItem.addStringProperty("bookNo");
        StudyGoodItem.addStringProperty("name");
        StudyGoodItem.addStringProperty("productId");

//        {
//            "itemNo": "1401101010206",
//                "stem": "2.0",
//                "hasStemPic": true,
//                "choiceA": "2+0",
//                "choiceB": "1+4",
//                "choiceC": "5+0",
//                "choiceD": "1+3",
//                "rightAnswer": "A"
//        },

//        {
        Entity calculationtest = schema.addEntity("CalculationTest");
        calculationtest.addIdProperty().autoincrement();
        calculationtest.addStringProperty("itemNo").notNull().unique();
        calculationtest.addStringProperty("stem");
        calculationtest.addBooleanProperty("hasStemPic");
        calculationtest.addStringProperty("picName");
        calculationtest.addBooleanProperty("hasStemSound");
        calculationtest.addStringProperty("soundName");
        calculationtest.addStringProperty("choiceA");
        calculationtest.addStringProperty("choiceB");
        calculationtest.addStringProperty("choiceC");
        calculationtest.addStringProperty("choiceD");
        calculationtest.addStringProperty("rightAnswer");


        Entity XTCSGJ = schema.addEntity("XTCSGJ");
        XTCSGJ.addIdProperty().autoincrement();
        XTCSGJ.addStringProperty("test_group_number");
        XTCSGJ.addStringProperty("user_id");
        XTCSGJ.addStringProperty("IsDoSubject");
        XTCSGJ.addStringProperty("IsGJ");
        XTCSGJ.addIntProperty("time");
        XTCSGJ.addDateProperty("testtime");
        XTCSGJ.addStringProperty("tableName");


        Entity XTCSJG = schema.addEntity("XTCSJG");
        XTCSJG.addIdProperty().autoincrement();
        XTCSJG.addIntProperty("xtcsjg_id");
        XTCSJG.addIntProperty("score");
        XTCSJG.addIntProperty("number_error");
        XTCSJG.addIntProperty("number_success");
        XTCSJG.addIntProperty("duration");
        XTCSJG.addDateProperty("jg_datetime");
        XTCSJG.addStringProperty("test_group_number");
        XTCSJG.addStringProperty("user_id");
        XTCSJG.addStringProperty("tableName");


        Entity XTCTJL = schema.addEntity("XTCTJL");
        XTCTJL.addIdProperty().autoincrement();
        XTCTJL.addIntProperty("xtcsjl_id");
        XTCTJL.addStringProperty("answer");
        XTCTJL.addDateProperty("jl_datetime");
        XTCTJL.addStringProperty("xtdctm_id");
        XTCTJL.addStringProperty("tableName");


        Entity XTDCTM = schema.addEntity("XTDCTM");
        XTDCTM.addIdProperty().autoincrement();
        XTDCTM.addStringProperty("xtdctm_id");
        XTDCTM.addStringProperty("examination_number");
        XTDCTM.addIntProperty("ismastered");
        XTDCTM.addStringProperty("test_group_number");
        XTDCTM.addStringProperty("user_id");
        XTDCTM.addStringProperty("tableName");


        Entity UserDoSubjectInfo = schema.addEntity("UserDoSubjectInfo");
        UserDoSubjectInfo.addIdProperty().autoincrement();
        UserDoSubjectInfo.addStringProperty("test_group_number");
        UserDoSubjectInfo.addStringProperty("user_id");
        UserDoSubjectInfo.addIntProperty("time");
        UserDoSubjectInfo.addDateProperty("testtime");
        UserDoSubjectInfo.addStringProperty("xtdctm_id");
        UserDoSubjectInfo.addStringProperty("examination_number");
        UserDoSubjectInfo.addIntProperty("ismastered");


        UserDoSubjectInfo.addIntProperty("xtcsjg_id");
        UserDoSubjectInfo.addIntProperty("score");
        UserDoSubjectInfo.addIntProperty("number_error");
        UserDoSubjectInfo.addIntProperty("number_success");
        UserDoSubjectInfo.addIntProperty("duration");
        UserDoSubjectInfo.addDateProperty("jg_datetime");

        UserDoSubjectInfo.addIntProperty("xtcsjl_id");
        UserDoSubjectInfo.addStringProperty("answer");
        UserDoSubjectInfo.addDateProperty("jl_datetime");
        UserDoSubjectInfo.addStringProperty("tableName");


        Entity Ranklist = schema.addEntity("Ranklist");
        Ranklist.addIdProperty().autoincrement();
        Ranklist.addStringProperty("unique_id");
        Ranklist.addStringProperty("integral_id").unique();
        Ranklist.addStringProperty("user_id");
        Ranklist.addIntProperty("integral_number");
        Ranklist.addStringProperty("month");
        Ranklist.addStringProperty("text");

        Ranklist.addStringProperty("real_name");
        Ranklist.addStringProperty("email");
        Ranklist.addStringProperty("class_number");
        Ranklist.addStringProperty("mobile");
        Ranklist.addStringProperty("password");
        Ranklist.addStringProperty("role");
        Ranklist.addDateProperty("indate");

        Ranklist.addStringProperty("user_icon_url");
        Ranklist.addStringProperty("gender");
        Ranklist.addStringProperty("qq_number");
        Ranklist.addStringProperty("weixin_number");
        Ranklist.addStringProperty("motto");
        Ranklist.addStringProperty("recipient");
        Ranklist.addStringProperty("address");
        Ranklist.addStringProperty("phone");
        Ranklist.addStringProperty("schoolName");


        Entity ErrorSubjectNub = schema.addEntity("ErrorSubjectNub");
        ErrorSubjectNub.addIdProperty().autoincrement();
        ErrorSubjectNub.addStringProperty("xtdctm_id");
        ErrorSubjectNub.addStringProperty("examination_number").unique();
        ErrorSubjectNub.addIntProperty("ismastered");
        ErrorSubjectNub.addStringProperty("test_group_number");
        ErrorSubjectNub.addStringProperty("user_id");
        ErrorSubjectNub.addIntProperty("xtcsjl_id");
        ErrorSubjectNub.addStringProperty("answer");
        ErrorSubjectNub.addStringProperty("jl_datetime");
        ErrorSubjectNub.addStringProperty("tableName");

        Entity Notification = schema.addEntity("Notification");
        Notification.addIdProperty().autoincrement();
        Notification.addStringProperty("notification_id").notNull();
        Notification.addStringProperty("titleNo");
        Notification.addStringProperty("title");
        Notification.addStringProperty("content");
        Notification.addStringProperty("notify_publisher");
        Notification.addDateProperty("notify_time");
        Notification.addStringProperty("notify_check_id");
        Notification.addStringProperty("is_read");
        Notification.addStringProperty("unique_id").notNull().unique();

        /**
         * 老师表结构
         * 	private String  teacherName;			//老师用户名
         private String  realName;				//真实姓名
         private String 	nickName;				//昵称
         private String  sex;					//性别
         private String  birthday;				//生日
         private String	motto;					//个性签名
         private String	teacher_icon_url;		//老师头像
         private String	password;				//密码
         private String	totalIntegral;			//总积分
         private String	userIntegral;			//用户积分

         private String	homeWorkIntegral;		//作业积分
         private String	articleIntegral;		//推荐文章积分
         private String	feedbackIntegral;		//反馈积分
         private String	schoolNum;				//学校编码
         private String	schoolName;				//学校名称
         private String	isClassTeacher;			//是否是班主任
         private String	subjectNum;				//科目编码
         private String	subjectName;			//科目名称

         private String	textBookNum;			//教材编号
         private String	textBookName;			//教材名称
         private String	classNum1;				//班级1编码
         private String	className1;				//班级1
         private String	peopleNum1;				//班级人数1
         private String	peopleRegister1;		//班级注册人数1
         private String	peopleBuy1;				//班级购买人数1
         private String	classNum2;				//班级2编码
         private String	className2;				//班级2
         private String	peopleNum2;				//班级人数2
         private String	peopleRegister2;		//班级注册人数2
         private String	peopleBuy2;				//班级购买人数2
         private String	classNum3;				//班级3编码
         private String	className3;				//班级3
         private String	peopleNum3;				//班级人数3
         private String	peopleRegister3;		//班级注册人数3
         private String	peopleBuy3;				//班级购买人数3
         private String	classNum4;				//班级4编码
         private String	className4;				//班级4
         private String	peopleNum4;				//班级人数4
         private String	peopleRegister4;		//班级注册人数4
         private String	peopleBuy4;				//班级购买人数4
         private String  qqNum;					//qq号码
         private String  weChatNum;				//微信号码
         private String  email;					//邮箱号码
         *
         */


        Entity teacher = schema.addEntity("teacher");
        teacher.addIdProperty().autoincrement();
        teacher.addStringProperty("teacherName").notNull();
        teacher.addStringProperty("realName");
        teacher.addStringProperty("nickName");
        teacher.addStringProperty("sex");
        teacher.addStringProperty("birthday");
        teacher.addStringProperty("motto");

        teacher.addStringProperty("teacher_icon_url");
        teacher.addStringProperty("password");
        teacher.addStringProperty("totalIntegral");
        teacher.addStringProperty("userIntegral");
        teacher.addStringProperty("homeWorkIntegral");
        teacher.addStringProperty("articleIntegral");
        teacher.addStringProperty("feedbackIntegral");
        teacher.addStringProperty("schoolNum");
        teacher.addStringProperty("schoolName");
        teacher.addStringProperty("isClassTeacher");

        teacher.addStringProperty("subjectNum");
        teacher.addStringProperty("subjectName");
        teacher.addStringProperty("textBookNum");
        teacher.addStringProperty("textBookName");
        teacher.addStringProperty("classNum1");
        teacher.addStringProperty("className1");
        teacher.addStringProperty("peopleNum1");
        teacher.addStringProperty("peopleRegister1");
        teacher.addStringProperty("peopleBuy1");
        teacher.addStringProperty("classNum2");
        teacher.addStringProperty("className2");
        teacher.addStringProperty("peopleNum2");
        teacher.addStringProperty("peopleRegister2");
        teacher.addStringProperty("peopleBuy2");
        teacher.addStringProperty("classNum3");
        teacher.addStringProperty("className3");
        teacher.addStringProperty("peopleNum3");
        teacher.addStringProperty("peopleRegister3");
        teacher.addStringProperty("peopleBuy3");
        teacher.addStringProperty("classNum4");
        teacher.addStringProperty("className4");
        teacher.addStringProperty("peopleNum4");
        teacher.addStringProperty("peopleRegister4");
        teacher.addStringProperty("peopleBuy4");
        teacher.addStringProperty("qqNum");
        teacher.addStringProperty("weChatNum");
        teacher.addStringProperty("email");


    }
}

