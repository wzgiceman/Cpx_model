package com.prog.zhigangwei.cpx_model.vpFragment.common.bean;

import java.util.List;

/**
 * Describe:
 * <p>
 * Created by zhigang wei
 * on 2018/12/5
 * <p>
 * Company :cpx
 */
public class WallType {

    private List<SubjectsBean> subjects;


    public List<SubjectsBean> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectsBean> subjects) {
        this.subjects = subjects;
    }

    public static class SlideshowsBean {
        /**
         * query : /shine/wallpapers/785
         * wallpapers : [{"id":785,"title":"878f8ed10a73eaaf839aca30ada21bd5","paper_type":"video","hot":10013,
         * "cover":"http://images.acgmonster.com/shine/wallpapers/878f8ed10a73eaaf839aca30ada21bd5.jpg!cover",
         * "url":"http://images.acgmonster.com/shine/wallpapers/878f8ed10a73eaaf839aca30ada21bd5.mp4","size":1801735,
         * "price":null,"sku_id":null,"long_create_at":1531376709000,"tag_list":["Fantasy"]}]
         * id : 785
         * cover : http://images.acgmonster.com/shine/wallpapers/878f8ed10a73eaaf839aca30ada21bd5.jpg!rotate_270
         */

        private String query;
        private int id;
        private String cover;
        private List<WallpapersBean> wallpapers;

        public String getQuery() {
            return query;
        }

        public void setQuery(String query) {
            this.query = query;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public List<WallpapersBean> getWallpapers() {
            return wallpapers;
        }

        public void setWallpapers(List<WallpapersBean> wallpapers) {
            this.wallpapers = wallpapers;
        }

        public static class WallpapersBean {
            /**
             * id : 785
             * title : 878f8ed10a73eaaf839aca30ada21bd5
             * paper_type : video
             * hot : 10013
             * cover : http://images.acgmonster.com/shine/wallpapers/878f8ed10a73eaaf839aca30ada21bd5.jpg!cover
             * url : http://images.acgmonster.com/shine/wallpapers/878f8ed10a73eaaf839aca30ada21bd5.mp4
             * size : 1801735
             * price : null
             * sku_id : null
             * long_create_at : 1531376709000
             * tag_list : ["Fantasy"]
             */

            private int id;
            private String title;
            private String paper_type;
            private int hot;
            private String cover;
            private String url;
            private int size;
            private Object price;
            private Object sku_id;
            private long long_create_at;
            private List<String> tag_list;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getPaper_type() {
                return paper_type;
            }

            public void setPaper_type(String paper_type) {
                this.paper_type = paper_type;
            }

            public int getHot() {
                return hot;
            }

            public void setHot(int hot) {
                this.hot = hot;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }

            public Object getPrice() {
                return price;
            }

            public void setPrice(Object price) {
                this.price = price;
            }

            public Object getSku_id() {
                return sku_id;
            }

            public void setSku_id(Object sku_id) {
                this.sku_id = sku_id;
            }

            public long getLong_create_at() {
                return long_create_at;
            }

            public void setLong_create_at(long long_create_at) {
                this.long_create_at = long_create_at;
            }

            public List<String> getTag_list() {
                return tag_list;
            }

            public void setTag_list(List<String> tag_list) {
                this.tag_list = tag_list;
            }
        }
    }

    public static class SubjectsBean {
        /**
         * name : Top Anime
         * query : /shine/wallpapers?q%5Btags_name_eq%5D=Anime
         * wallpapers : [{"id":1117,"title":"feedd673de4db8e34b04a8aea908f1ba","paper_type":"video","hot":3510,
         * "cover":"http://images.acgmonster.com/shine/wallpapers/feedd673de4db8e34b04a8aea908f1ba.jpg!cover",
         * "url":"http://images.acgmonster.com/shine/wallpapers/feedd673de4db8e34b04a8aea908f1ba.mp4","size":1249586,
         * "price":null,"sku_id":null,"long_create_at":1537339510000,"tag_list":["Anime"]},{"id":1148,
         * "title":"6bb5d9bf855e67e06ec102f9a6276ba3","paper_type":"video","hot":2864,"cover":"http://images.acgmonster
         * .com/shine/wallpapers/6bb5d9bf855e67e06ec102f9a6276ba3.jpg!cover","url":"http://images.acgmonster
         * .com/shine/wallpapers/6bb5d9bf855e67e06ec102f9a6276ba3.mp4","size":4737098,"price":null,"sku_id":null,
         * "long_create_at":1542633833000,"tag_list":["Anime"]},{"id":1144,"title":"1bd8d5d22522d728f6925e43678d860b",
         * "paper_type":"video","hot":7198,"cover":"http://images.acgmonster
         * .com/shine/wallpapers/1bd8d5d22522d728f6925e43678d860b.jpg!cover","url":"http://images.acgmonster
         * .com/shine/wallpapers/1bd8d5d22522d728f6925e43678d860b.mp4","size":1514675,"price":null,"sku_id":null,
         * "long_create_at":1541476209000,"tag_list":["Anime"]}]
         */

        private String name;
        private String query;
        private List<WallpapersBeanX> wallpapers;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getQuery() {
            return query;
        }

        public void setQuery(String query) {
            this.query = query;
        }

        public List<WallpapersBeanX> getWallpapers() {
            return wallpapers;
        }

        public void setWallpapers(List<WallpapersBeanX> wallpapers) {
            this.wallpapers = wallpapers;
        }

        public static class WallpapersBeanX {
            /**
             * id : 1117
             * title : feedd673de4db8e34b04a8aea908f1ba
             * paper_type : video
             * hot : 3510
             * cover : http://images.acgmonster.com/shine/wallpapers/feedd673de4db8e34b04a8aea908f1ba.jpg!cover
             * url : http://images.acgmonster.com/shine/wallpapers/feedd673de4db8e34b04a8aea908f1ba.mp4
             * size : 1249586
             * price : null
             * sku_id : null
             * long_create_at : 1537339510000
             * tag_list : ["Anime"]
             */

            private int id;
            private String title;
            private String paper_type;
            private int hot;
            private String cover;
            private String url;
            private int size;
            private Object price;
            private Object sku_id;
            private long long_create_at;
            private List<String> tag_list;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getPaper_type() {
                return paper_type;
            }

            public void setPaper_type(String paper_type) {
                this.paper_type = paper_type;
            }

            public int getHot() {
                return hot;
            }

            public void setHot(int hot) {
                this.hot = hot;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }

            public Object getPrice() {
                return price;
            }

            public void setPrice(Object price) {
                this.price = price;
            }

            public Object getSku_id() {
                return sku_id;
            }

            public void setSku_id(Object sku_id) {
                this.sku_id = sku_id;
            }

            public long getLong_create_at() {
                return long_create_at;
            }

            public void setLong_create_at(long long_create_at) {
                this.long_create_at = long_create_at;
            }

            public List<String> getTag_list() {
                return tag_list;
            }

            public void setTag_list(List<String> tag_list) {
                this.tag_list = tag_list;
            }
        }
    }

    public static class TagsBean {
        /**
         * id : 19
         * name : Abstract
         */

        private int id;
        private String name;

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
    }
}
