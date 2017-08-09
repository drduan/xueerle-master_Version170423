package com.neusoft.sample.Model;

/**
 * Created by Administrator on 2016/7/9.
 */
public class TemporaryCode {

//
//    @Override
//    public int compareTo(TextOneStructure another) {
//        if (showSequence.intValue()<another.getShowSequence().intValue())
//            return -1;
//        else if (showSequence.intValue()==another.getShowSequence().intValue())return 0;
//
//        else return 1;
//    }

//    @Override
//    public int compareTo(TextTwoStructure another) {
//        if (showSequence.intValue()<another.showSequence.intValue())return -1;
//        else  if (showSequence.intValue()==another.showSequence.intValue())return 0;
//        else return 1;
//    }




    /**
     * 這個是login
     */


    //            String mphone = login_phone.getText().toString();
//            Map<String, String> paramMaps =new HashMap<>();
//
//            List<User> list2 = Db_UserService.getInstance(this).loadAllNote();//user表的list
//            for (User users1 : list2) {
//
//                if (users1.getPhone().equals(mphone)) {//手机存在
//                    paramMaps.put("user_id", users1.getServer_id());
//                }
//            }
//            Log.d("Test_","走到了");
//            PostUsertoID.Linkage(this, Constant.Post_url_For_UsertoID, paramMaps);


//            String mphone = login_phone.getText().toString();
//            List<User> list = Db_UserService.getInstance(this).loadAllNote();//user表的list
//            for (User user : list) {
//
//                if (user.getPhone().equals(mphone)) {//手机存在
//                    String nub=  user.getJiaocai_nub();
//                    List<StudyGoodTerm> list1= Db_StudyGoodTermService.getInstance(this).loadAllNote();//教材表的list
//                    for (StudyGoodTerm studyGoodTerm:list1)
//                    {
//                        if (nub.equals(studyGoodTerm.getBookNo()))//判断其教材中的号是否对应
//                        {//存在时
//                            //验证用户的产品有效性
//                            List<StudyGoodItem> listitem= Db_StudyGoodItemService.getInstance(this).loadAllNote();
//                            for (StudyGoodItem studyGoodItem:listitem)
//                            {
//                                if (studyGoodTerm.getBookNo().equals(studyGoodItem.getBookNo()))
//                                {
//                                    List<StudyGoodInfo> list2= Db_StudyGoodInfoService.getInstance(this).loadAllNote();
//                                    for (StudyGoodInfo info:list2) {
//                                        if (studyGoodItem.getProductId().equals(info.getProductNo()))
//                                        {
//                                            //刚从服务器中获取内容
//                                            //核对用户的有效期
//                                            //TODO******************************网络时间***************************
//                                            if (Constant.getyy_mm_dd(info.getDueDate()).equals(""))//在有效期内
//                                            {
//                                                Log.d("Vaild","是刚刚down下来的并且没过期");
//                                                return;
//                                            }
//
//                                        }
//                                    }
//                                }
//                            }
//
//
//
//
//
//
//                        }
//                        else{
//                            //不存在时
//                            //上传用户的ID
//
//
//                            Map<String, String> paramMaps =new HashMap<>();
//
//                            List<User> list2 = Db_UserService.getInstance(this).loadAllNote();//user表的list
//                            for (User users1 : list) {
//
//                                if (users1.getPhone().equals(mphone)) {//手机存在
//                                    paramMaps.put("user_id", users1.getServer_id());
//                                }
//                            }
//
//                            PostUsertoID.Linkage(this, url, paramMaps);
//
//                        }
//                    }
//                }
//            }


///**
// * 请不要删除
// * 下面的意思是：
// * 当我要用你的手机学习我的内容时，
// * 不会把你手机上你的数据库信息删除
// * 而我的内容则会再追加到数据库中
// */
//            String mphone = login_phone.getText().toString();
//            List<User> list = Db_UserService.getInstance(this).loadAllNote();//user表的list
//            for (User user : list) {
//
//                if (user.getPhone().equals(mphone)) {//手机存在
//                    String nub=  user.getProductNo();
//                    List<StudyGoodInfo> list1= Db_StudyGoodInfoService.getInstance(this).loadAllNote();//教材表的list
//                    for (StudyGoodInfo studyGoodInfo:list1)
//                    {
//                        if (nub.equals(studyGoodInfo.getProductNo()))//判断其教材中的号是否对应
//                        {//存在时
//                            //验证用户的产品有效性
//                            List<StudyGoodItem> listitem= Db_StudyGoodItemService.getInstance(this).loadAllNote();
//                            for (StudyGoodItem studyGoodItem:listitem)
//                            {
//                                if (studyGoodTerm.getBookNo().equals(studyGoodItem.getBookNo()))
//                                {
//                                    List<StudyGoodInfo> list2= Db_StudyGoodInfoService.getInstance(this).loadAllNote();
//                                    for (StudyGoodInfo info:list2) {
//                                        if (studyGoodItem.getProductId().equals(info.getProductNo()))
//                                        {
//                                            //刚从服务器中获取内容
//                                            //核对用户的有效期
//
//                                               if (Constant.getyymmdd(info.getDueDate()).equals(Constant.getTime()))//在有效期内
//                                               {
//                                               Log.d("Vaild","是刚刚down下来的并且没过期");
//                                                   return;
//                                               }
//
//                                        }
//                                    }
//                                }
//                            }
//
//
//
//
//
//
//                        }
//                        else{
//                            //不存在时
//                            //上传用户的ID
//
//
//                            Map<String, String> paramMaps =new HashMap<>();
//
//                            List<User> list2 = Db_UserService.getInstance(this).loadAllNote();//user表的list
//                            for (User users1 : list2) {
//
//                                if (users1.getPhone().equals(mphone)) {//手机存在
//                                    paramMaps.put("user_id", users1.getServer_id());
//                                }
//                            }
//
//                            PostUsertoID.Linkage(this, Constant.Post_url_For_UsertoID, paramMaps);
//
//                        }
//                    }
//                }
//            }
}
