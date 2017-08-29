package com.linkhand.baixingguanjia.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JCY on 2017/7/20.
 * 说明：
 */

public class TestJson implements Serializable{

    /**
     * id : 1
     * name : 河北省
     * fu_id : 0
     * shi : [{"id":2,"name":"石家庄市","fu_id":1,"qu":[{"id":3,"name":"裕华区","fu_id":2,"xiaoqu":[{"id":4,"name":"华山商务","fu_id":3},{"id":15,"name":"测试小区","fu_id":3}]},{"id":5,"name":"长安区","fu_id":2,"xiaoqu":[{"id":6,"name":"建华百货","fu_id":5}]},{"id":10,"name":"桥西区","fu_id":2,"xiaoqu":[{"id":11,"name":"美嘉大厦","fu_id":10}]},{"id":12,"name":"平山县","fu_id":2,"xiaoqu":[{"id":13,"name":"平山小区","fu_id":12}]},{"id":14,"name":"新华区","fu_id":2,"xiaoqu":[{"id":28,"name":"新华小区","fu_id":14}]}]}]
     */

    private int id;
    private String name;
    private int fu_id;
    private List<ShiBean> shi;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFu_id() {
        return fu_id;
    }

    public void setFu_id(int fu_id) {
        this.fu_id = fu_id;
    }

    public List<ShiBean> getShi() {
        return shi;
    }

    public void setShi(List<ShiBean> shi) {
        this.shi = shi;
    }

    public static class ShiBean {
        /**
         * id : 2
         * name : 石家庄市
         * fu_id : 1
         * qu : [{"id":3,"name":"裕华区","fu_id":2,"xiaoqu":[{"id":4,"name":"华山商务","fu_id":3},{"id":15,"name":"测试小区","fu_id":3}]},{"id":5,"name":"长安区","fu_id":2,"xiaoqu":[{"id":6,"name":"建华百货","fu_id":5}]},{"id":10,"name":"桥西区","fu_id":2,"xiaoqu":[{"id":11,"name":"美嘉大厦","fu_id":10}]},{"id":12,"name":"平山县","fu_id":2,"xiaoqu":[{"id":13,"name":"平山小区","fu_id":12}]},{"id":14,"name":"新华区","fu_id":2,"xiaoqu":[{"id":28,"name":"新华小区","fu_id":14}]}]
         */

        private int id;
        private String name;
        private int fu_id;
        private List<QuBean> qu;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getFu_id() {
            return fu_id;
        }

        public void setFu_id(int fu_id) {
            this.fu_id = fu_id;
        }

        public List<QuBean> getQu() {
            return qu;
        }

        public void setQu(List<QuBean> qu) {
            this.qu = qu;
        }

        public static class QuBean {
            /**
             * id : 3
             * name : 裕华区
             * fu_id : 2
             * xiaoqu : [{"id":4,"name":"华山商务","fu_id":3},{"id":15,"name":"测试小区","fu_id":3}]
             */

            private int id;
            private String name;
            private int fu_id;
            private List<XiaoquBean> xiaoqu;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getFu_id() {
                return fu_id;
            }

            public void setFu_id(int fu_id) {
                this.fu_id = fu_id;
            }

            public List<XiaoquBean> getXiaoqu() {
                return xiaoqu;
            }

            public void setXiaoqu(List<XiaoquBean> xiaoqu) {
                this.xiaoqu = xiaoqu;
            }

            public static class XiaoquBean {
                /**
                 * id : 4
                 * name : 华山商务
                 * fu_id : 3
                 */

                private int id;
                private String name;
                private int fu_id;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getFu_id() {
                    return fu_id;
                }

                public void setFu_id(int fu_id) {
                    this.fu_id = fu_id;
                }
            }
        }
    }
}
