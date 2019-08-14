package com.wei.boot.lambda;

import com.wei.boot.bean.UserInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: weisihua
 * @Date: 2019-08-08 19:36
 **/
public class FilterMapTest {
    public static void main(String[] args) {
        List<UserInfo> userList = new ArrayList<>();
        UserInfo u1 = new UserInfo(1,"zhangsan");
        UserInfo u2 = new UserInfo(2,"a");
        UserInfo u3 = new UserInfo(3,"bc");
        userList.add(u1);
        userList.add(u2);
        userList.add(u3);

        List<String> nameList = userList.stream().filter(u -> u.getName().length() < 3).map(UserInfo::getName).collect(Collectors.toList());
        System.out.println(nameList);

        userList.stream().map(UserInfo::getName).filter(u -> u.length() < 3).forEach(each -> System.out.println(each));
        //System.out.println(nameList2);
    }
}
