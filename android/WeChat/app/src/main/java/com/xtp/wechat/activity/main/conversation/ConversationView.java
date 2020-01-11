package com.xtp.wechat.activity.main.conversation;

import com.xtp.wechat.BaseView;

public interface ConversationView extends BaseView<ConversationPresenter> {
    //void showItemDialog(HTConversation htConversation);
    void refresh();

    public interface NewMeesageListener {
        //返回多少条未读消息
        void onUnReadMsgs(int count);
    }
}
