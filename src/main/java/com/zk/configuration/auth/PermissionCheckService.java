package com.zk.configuration.auth;

import com.zk.common.context.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

// service取名sc是方便注解调用
@Service("pm")
@Slf4j
public class PermissionCheckService {

    public boolean check(String permission) {
        Long userId = UserContext.getId();

        Map<String, String> userPermissions = new HashMap<>();
        // todo 通过用户id从其他地方拿认证
        userPermissions.put("test", "test");
        if (userPermissions.containsKey(permission))
            return true;
        else
            return false;
    }
}