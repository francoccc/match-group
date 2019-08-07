package com.franco.group;

import com.franco.common.Tuple;
import com.franco.domain.Matchable;
import com.franco.listener.MatchListener;

import java.util.EventObject;
import java.util.List;

public interface MatchGroup {

    /**
     * 增加一个匹配对象
     * @param obj
     * @return
     */
    boolean add(Matchable obj);

    void pushEvent(EventObject object);

    MatchGroup addListener(MatchListener matchListener);

    /**
     * 匹配
     * @return
     */
    int match();

    /**
     * 通知匹配对象
     */
    void notifyMatcher(List<Tuple<Matchable, Matchable>> matchers);

    /**
     * 处理添加事件
     */
    void onHandleAddEvent();

    void onHandleIdleEvent();
}
