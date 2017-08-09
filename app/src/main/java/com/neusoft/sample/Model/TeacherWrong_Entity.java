package com.neusoft.sample.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2016/12/24.
 */

public class TeacherWrong_Entity  implements Parcelable{


    /**
     * data : [{"test_group_number":"14051010111","examination_number":"1405101011126","count":"3","stem":"56÷5"},{"test_group_number":"14051010116","examination_number":"140510101161","count":"1","stem":"25.2÷4"},{"test_group_number":"14051010116","examination_number":"1405101011623","count":"1","stem":"1.8÷3"},{"test_group_number":"14051010116","examination_number":"140510101166","count":"1","stem":"3.6÷6"},{"test_group_number":"14051010112","examination_number":"1405101011210","count":"1","stem":"44.1÷7"},{"test_group_number":"14051010161","examination_number":"1405101016127","count":"1","stem":"最小的是"},{"test_group_number":"14051010111","examination_number":"140510101111","count":"4","stem":"6÷5"},{"test_group_number":"14051010112","examination_number":"1405101011222","count":"1","stem":"9.6÷4"},{"test_group_number":"14051010112","examination_number":"140510101126","count":"1","stem":"22.4÷4"},{"test_group_number":"14051010116","examination_number":"1405101011629","count":"1","stem":"1÷5"},{"test_group_number":"14051010111","examination_number":"1405101011121","count":"5","stem":"6.5÷5"},{"test_group_number":"14051010141","examination_number":"1405101014111","count":"1","stem":"1.7÷2"},{"test_group_number":"14051010111","examination_number":"1405101011122","count":"2","stem":"99.9÷3"},{"test_group_number":"14051010112","examination_number":"140510101124","count":"1","stem":"31.8÷3"},{"test_group_number":"14051010111","examination_number":"1405101011123","count":"6","stem":"1.2÷6"},{"test_group_number":"14051010116","examination_number":"1405101011624","count":"1","stem":"88.4÷4"},{"test_group_number":"14051010165","examination_number":"1405101016519","count":"1","stem":"最小的是"},{"test_group_number":"14051010111","examination_number":"1405101011110","count":"2","stem":"28.7÷7"},{"test_group_number":"14051010112","examination_number":"1405101011224","count":"1","stem":"9.6÷6"},{"test_group_number":"14051010111","examination_number":"1405101011130","count":"4","stem":"25.2÷4"},{"test_group_number":"14051010168","examination_number":"1405101016821","count":"1","stem":"最小的是"},{"test_group_number":"14051010116","examination_number":"140510101168","count":"1","stem":"11.2÷4"},{"test_group_number":"14051010168","examination_number":"140510101683","count":"1","stem":"最小的是"},{"test_group_number":"14051010111","examination_number":"1405101011113","count":"1","stem":"32.4÷3"},{"test_group_number":"14051010112","examination_number":"1405101011215","count":"1","stem":"88.8÷8"},{"test_group_number":"14051010165","examination_number":"140510101656","count":"1","stem":"最小的是"},{"test_group_number":"14051010168","examination_number":"140510101686","count":"1","stem":"最小的是"},{"test_group_number":"14051010112","examination_number":"1405101011212","count":"1","stem":"32.8÷4"},{"test_group_number":"14051010111","examination_number":"1405101011129","count":"1","stem":"8.1÷9"},{"test_group_number":"14051010141","examination_number":"1405101014117","count":"1","stem":"3.5÷5"},{"test_group_number":"14051010165","examination_number":"1405101016518","count":"1","stem":"最小的是"},{"test_group_number":"14051010165","examination_number":"1405101016525","count":"1","stem":"最小的是"},{"test_group_number":"14051010168","examination_number":"1405101016830","count":"1","stem":"最小的是"},{"test_group_number":"14051010116","examination_number":"1405101011615","count":"1","stem":"66.6÷6"},{"test_group_number":"14051010165","examination_number":"1405101016516","count":"1","stem":"最小的是"},{"test_group_number":"14051010146","examination_number":"140510101462","count":"1","stem":"10.4÷2"},{"test_group_number":"14051010168","examination_number":"1405101016816","count":"1","stem":"最小的是"},{"test_group_number":"14051010111","examination_number":"140510101119","count":"2","stem":"77.7÷7"},{"test_group_number":"14051010168","examination_number":"1405101016824","count":"1","stem":"最小的是"},{"test_group_number":"14051010165","examination_number":"1405101016524","count":"1","stem":"最小的是"},{"test_group_number":"14051010161","examination_number":"1405101016124","count":"1","stem":"最小的是"},{"test_group_number":"14051010116","examination_number":"1405101011620","count":"1","stem":"12.3÷3"},{"test_group_number":"14051010161","examination_number":"1405101016112","count":"1","stem":"最小的是"},{"test_group_number":"14051010146","examination_number":"140510101461","count":"1","stem":"2.04÷1"},{"test_group_number":"14051010111","examination_number":"1405101011125","count":"3","stem":"13.6÷8"},{"test_group_number":"14051010111","examination_number":"140510101113","count":"1","stem":"55.5÷5"},{"test_group_number":"14051010161","examination_number":"1405101016117","count":"1","stem":"最小的是"},{"test_group_number":"14051010116","examination_number":"1405101011619","count":"1","stem":"77.7÷7"},{"test_group_number":"14051010168","examination_number":"140510101687","count":"1","stem":"最小的是"},{"test_group_number":"14051010116","examination_number":"1405101011616","count":"1","stem":"11÷5"},{"test_group_number":"14051010111","examination_number":"140510101118","count":"1","stem":"8÷5"},{"test_group_number":"14051010165","examination_number":"1405101016530","count":"1","stem":"最小的是"},{"test_group_number":"14051010168","examination_number":"140510101688","count":"1","stem":"最小的是"},{"test_group_number":"14051010116","examination_number":"1405101011626","count":"1","stem":"44.8÷8"},{"test_group_number":"14051010116","examination_number":"1405101011617","count":"1","stem":"7.6÷4"},{"test_group_number":"14051010111","examination_number":"1405101011112","count":"1","stem":"23.8÷7"},{"test_group_number":"14051010165","examination_number":"1405101016511","count":"1","stem":"最小的是"},{"test_group_number":"14051010116","examination_number":"1405101011618","count":"1","stem":"12.3÷3"},{"test_group_number":"14051010112","examination_number":"1405101011228","count":"1","stem":"4.4÷4"},{"test_group_number":"14051010116","examination_number":"1405101011614","count":"1","stem":"9.2÷4"},{"test_group_number":"14051010116","examination_number":"140510101165","count":"1","stem":"23÷5"},{"test_group_number":"14051010111","examination_number":"140510101114","count":"2","stem":"25.8÷3"},{"test_group_number":"14051010168","examination_number":"1405101016810","count":"1","stem":"最小的是"},{"test_group_number":"14051010111","examination_number":"1405101011118","count":"3","stem":"9.3÷3"},{"test_group_number":"14051010116","examination_number":"1405101011612","count":"1","stem":"4.8÷6"},{"test_group_number":"14051010111","examination_number":"1405101011116","count":"1","stem":"27.6÷3"},{"test_group_number":"14051010111","examination_number":"140510101112","count":"2","stem":"6.3÷7"},{"test_group_number":"14051010116","examination_number":"1405101011621","count":"1","stem":"26.8÷4"},{"test_group_number":"14051010111","examination_number":"1405101011120","count":"1","stem":"26.4÷4"},{"test_group_number":"14051010165","examination_number":"140510101659","count":"1","stem":"最小的是"},{"test_group_number":"14051010116","examination_number":"140510101169","count":"1","stem":"28.7÷7"},{"test_group_number":"14051010112","examination_number":"140510101127","count":"1","stem":"9.9÷9"},{"test_group_number":"14051010168","examination_number":"1405101016825","count":"1","stem":"最小的是"},{"test_group_number":"14051010112","examination_number":"1405101011214","count":"1","stem":"12.6÷3"},{"test_group_number":"14051010116","examination_number":"1405101011610","count":"1","stem":"13.2÷4"},{"test_group_number":"14051010111","examination_number":"1405101011127","count":"1","stem":"24.4÷4"},{"test_group_number":"14051010145","examination_number":"1405101014514","count":"1","stem":"1.67÷1"},{"test_group_number":"14051010116","examination_number":"1405101011628","count":"1","stem":"3.4÷17"},{"test_group_number":"14051010111","examination_number":"140510101117","count":"4","stem":"26.4÷8"},{"test_group_number":"14051010112","examination_number":"1405101011220","count":"1","stem":"4.5÷9"},{"test_group_number":"14051010111","examination_number":"1405101011111","count":"3","stem":"8.4÷7"},{"test_group_number":"14051010112","examination_number":"1405101011223","count":"1","stem":"36.6÷6"},{"test_group_number":"14051010111","examination_number":"140510101115","count":"4","stem":"44.4÷3"},{"test_group_number":"14051010111","examination_number":"1405101011128","count":"3","stem":"21.2÷4"},{"test_group_number":"14051010161","examination_number":"1405101016115","count":"1","stem":"最小的是"},{"test_group_number":"14051010161","examination_number":"1405101016125","count":"1","stem":"最小的是"},{"test_group_number":"14051010111","examination_number":"1405101011115","count":"4","stem":"7÷2"},{"test_group_number":"14051010145","examination_number":"140510101453","count":"1","stem":"10.3÷1"},{"test_group_number":"14051010111","examination_number":"1405101011124","count":"1","stem":"11.7÷11.7"},{"test_group_number":"14051010116","examination_number":"1405101011613","count":"1","stem":"12.3÷3"},{"test_group_number":"14051010111","examination_number":"1405101011119","count":"5","stem":"4÷5"},{"test_group_number":"14051010112","examination_number":"140510101121","count":"1","stem":"29.4÷7"},{"test_group_number":"14051010165","examination_number":"140510101657","count":"1","stem":"最小的是"},{"test_group_number":"14051010111","examination_number":"1405101011114","count":"3","stem":"9.6÷3"},{"test_group_number":"14051010111","examination_number":"140510101116","count":"2","stem":"33.9÷3"},{"test_group_number":"14051010165","examination_number":"1405101016512","count":"1","stem":"最小的是"},{"test_group_number":"14051010111","examination_number":"1405101011117","count":"1","stem":"12.4÷4"},{"test_group_number":"14051010112","examination_number":"1405101011211","count":"1","stem":"32.8÷4"},{"test_group_number":"14051010145","examination_number":"140510101452","count":"1","stem":"1.6÷2"},{"test_group_number":"14051010165","examination_number":"140510101652","count":"1","stem":"最小的是"},{"test_group_number":"14051010161","examination_number":"1405101016118","count":"1","stem":"最小的是"},{"test_group_number":"14051010146","examination_number":"1405101014630","count":"1","stem":"4.4÷4"}]
     * success : 200
     * error :
     */

    private int success;
    private String error;
    private List<DataBean> data;

    protected TeacherWrong_Entity(Parcel in) {
        success = in.readInt();
        error = in.readString();
    }

    public static final Creator<TeacherWrong_Entity> CREATOR = new Creator<TeacherWrong_Entity>() {
        @Override
        public TeacherWrong_Entity createFromParcel(Parcel in) {
            return new TeacherWrong_Entity(in);
        }

        @Override
        public TeacherWrong_Entity[] newArray(int size) {
            return new TeacherWrong_Entity[size];
        }
    };

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(success);
        dest.writeString(error);
    }

    public static class DataBean implements Parcelable{
        /**
         * test_group_number : 14051010111
         * examination_number : 1405101011126
         * count : 3
         * stem : 56÷5
         */

        private String test_group_number;
        private String examination_number;
        private String count;
        private String stem;

        protected DataBean(Parcel in) {
            test_group_number = in.readString();
            examination_number = in.readString();
            count = in.readString();
            stem = in.readString();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

        public String getTest_group_number() {
            return test_group_number;
        }

        public void setTest_group_number(String test_group_number) {
            this.test_group_number = test_group_number;
        }

        public String getExamination_number() {
            return examination_number;
        }

        public void setExamination_number(String examination_number) {
            this.examination_number = examination_number;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getStem() {
            return stem;
        }

        public void setStem(String stem) {
            this.stem = stem;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(test_group_number);
            dest.writeString(examination_number);
            dest.writeString(count);
            dest.writeString(stem);
        }
    }
}
