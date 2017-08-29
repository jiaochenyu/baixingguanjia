package com.linkhand.baixingguanjia.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JCY on 2017/7/21.
 * 说明：
 */

public class GoodsTag implements Serializable {

    /**
     * name : 大小
     * item : 小
     * id : 1
     */
    private List<Guige> mGuiges;

    public List<Guige> getGuiges() {
        return mGuiges;
    }

    public void setGuiges(List<Guige> guiges) {
        mGuiges = guiges;
    }

    //规格
    public static class Guige implements Serializable{
        private String name;
        private String item;
        private String item_id;
        private boolean flag = false;
        private String src;


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }

        public String getItem_id() {
            return item_id;
        }
        public void setItem_id(String id) {
            this.item_id = id;
        }

        public boolean isFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        @Override
        public String toString() {
            return "Guige{" +
                    "name='" + name + '\'' +
                    ", item='" + item + '\'' +
                    ", id='" + item_id + '\'' +
                    ", flag=" + flag +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GoodsTag{" +
                "mGuiges=" + mGuiges +
                '}';
    }
}
