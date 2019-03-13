package com.prog.zhigangwei.cpx_model.youtube.common.bean;

import java.util.List;

/**
 * Description:
 * YouTubeApi返回的结果
 *
 * @author Alpinist Wang
 * Company: Mobile CPX
 * Date:    2019/2/12
 */
public class YouTubeVideoResult {

    /**
     * errorCode : 0
     * errorMessage :
     * content : {"next_page_token":"CAUQAA","items":[{"video_id":"KLRmRpH8XmA","publish_time":"2019-01-17T05:27:53.000Z","title":"Funny Videos 2019 ● People doing stupid things P11","description":"Hi my friends, please check our new compilation. Here is moments people funny videos and we hope that this video make your life more fun & you enjoy it ...","thumbnail_url":"https://i.ytimg.com/vi/KLRmRpH8XmA/default.jpg","channel_title":"Vines best fun"},{"video_id":"35yd_H5oQ6U","publish_time":"2018-11-15T20:00:00.000Z","title":"TRY NOT TO LAUGH - Funny FAILS VINES | Funny Videos November 2018","description":"Brand new weekly fail compilation of the funniest Fails of the week for November 2018. Selection includes kids getting owned, trick shots gone wrong, home ...","thumbnail_url":"https://i.ytimg.com/vi/35yd_H5oQ6U/default.jpg","channel_title":"Funny Vines"},{"video_id":"pJfNAxlrPxU","publish_time":"2018-12-16T05:42:11.000Z","title":"Must Watch New Funny😂 😂Comedy Videos 2018 - Episode 10 - Funny Vines || SM TV","description":"Must Watch New Funny   Comedy Videos 2018 - Episode 10 - Funny Vines || SM TV ➤About Channel - In our channel you will see all Latest Videos, We make ...","thumbnail_url":"https://i.ytimg.com/vi/pJfNAxlrPxU/default.jpg","channel_title":"SM TV"},{"video_id":"WzueL4wijCQ","publish_time":"2018-12-20T20:20:10.000Z","title":"TOP 1000 FUNNIEST MOMENTS IN FORTNITE 🔥","description":"TOP 1000 FUNNIEST MOMENTS IN FORTNITE Subscribe - https://goo.gl/KUm8Qk WATCH ALL OF OUR FORNITE FAILS: ...","thumbnail_url":"https://i.ytimg.com/vi/WzueL4wijCQ/default_live.jpg","channel_title":"Fortnite 247"},{"video_id":"XTHr4kjVj2o","publish_time":"2019-02-09T20:00:03.000Z","title":"TRY NOT TO LAUGH - EPIC FAILS Vines | Funny Videos February 2019","description":"Brand new weekly fail compilation of the funniest Fails of the week for January 2019. Selection includes kids getting owned, trick shots gone wrong, home video ...","thumbnail_url":"https://i.ytimg.com/vi/XTHr4kjVj2o/default.jpg","channel_title":"Funny Vines"}]}
     */

    private int errorCode;
    private String errorMessage;
    private ContentBean content;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * next_page_token : CAUQAA
         * items : [{"video_id":"KLRmRpH8XmA","publish_time":"2019-01-17T05:27:53.000Z","title":"Funny Videos 2019 ● People doing stupid things P11","description":"Hi my friends, please check our new compilation. Here is moments people funny videos and we hope that this video make your life more fun & you enjoy it ...","thumbnail_url":"https://i.ytimg.com/vi/KLRmRpH8XmA/default.jpg","channel_title":"Vines best fun"},{"video_id":"35yd_H5oQ6U","publish_time":"2018-11-15T20:00:00.000Z","title":"TRY NOT TO LAUGH - Funny FAILS VINES | Funny Videos November 2018","description":"Brand new weekly fail compilation of the funniest Fails of the week for November 2018. Selection includes kids getting owned, trick shots gone wrong, home ...","thumbnail_url":"https://i.ytimg.com/vi/35yd_H5oQ6U/default.jpg","channel_title":"Funny Vines"},{"video_id":"pJfNAxlrPxU","publish_time":"2018-12-16T05:42:11.000Z","title":"Must Watch New Funny😂 😂Comedy Videos 2018 - Episode 10 - Funny Vines || SM TV","description":"Must Watch New Funny   Comedy Videos 2018 - Episode 10 - Funny Vines || SM TV ➤About Channel - In our channel you will see all Latest Videos, We make ...","thumbnail_url":"https://i.ytimg.com/vi/pJfNAxlrPxU/default.jpg","channel_title":"SM TV"},{"video_id":"WzueL4wijCQ","publish_time":"2018-12-20T20:20:10.000Z","title":"TOP 1000 FUNNIEST MOMENTS IN FORTNITE 🔥","description":"TOP 1000 FUNNIEST MOMENTS IN FORTNITE Subscribe - https://goo.gl/KUm8Qk WATCH ALL OF OUR FORNITE FAILS: ...","thumbnail_url":"https://i.ytimg.com/vi/WzueL4wijCQ/default_live.jpg","channel_title":"Fortnite 247"},{"video_id":"XTHr4kjVj2o","publish_time":"2019-02-09T20:00:03.000Z","title":"TRY NOT TO LAUGH - EPIC FAILS Vines | Funny Videos February 2019","description":"Brand new weekly fail compilation of the funniest Fails of the week for January 2019. Selection includes kids getting owned, trick shots gone wrong, home video ...","thumbnail_url":"https://i.ytimg.com/vi/XTHr4kjVj2o/default.jpg","channel_title":"Funny Vines"}]
         */

        private String next_page_token;
        private List<ItemsBean> items;

        public String getNext_page_token() {
            return next_page_token;
        }

        public void setNext_page_token(String next_page_token) {
            this.next_page_token = next_page_token;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            /**
             * video_id : KLRmRpH8XmA
             * publish_time : 2019-01-17T05:27:53.000Z
             * title : Funny Videos 2019 ● People doing stupid things P11
             * description : Hi my friends, please check our new compilation. Here is moments people funny videos and we hope that this video make your life more fun & you enjoy it ...
             * thumbnail_url : https://i.ytimg.com/vi/KLRmRpH8XmA/default.jpg
             * channel_title : Vines best fun
             */

            private String video_id;
            private long publish_time;
            private String title;
            private String description;
            private String thumbnail_url;
            private String channel_title;

            public String getVideo_id() {
                return video_id;
            }

            public void setVideo_id(String video_id) {
                this.video_id = video_id;
            }


            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getThumbnail_url() {
                return thumbnail_url;
            }

            public void setThumbnail_url(String thumbnail_url) {
                this.thumbnail_url = thumbnail_url;
            }

            public String getChannel_title() {
                return channel_title;
            }

            public void setChannel_title(String channel_title) {
                this.channel_title = channel_title;
            }

            public long getPublish_time() {
                return publish_time;
            }

            public void setPublish_time(long publish_time) {
                this.publish_time = publish_time;
            }
        }
    }
}
