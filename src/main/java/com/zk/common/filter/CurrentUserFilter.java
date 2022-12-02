package com.zk.common.filter;

import cn.hutool.json.JSONUtil;
import com.zk.common.context.CurrentUser;
import com.zk.common.context.UserContext;
import com.zk.common.exception.ErrorCode;
import com.zk.common.exception.ServiceException;
import com.zk.common.properties.URIProperties;
import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.SignatureVerifier;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

@Component
@Slf4j
public class CurrentUserFilter extends OncePerRequestFilter {
    @Resource
    private URIProperties urlProperties;
    @Resource
    private SignatureVerifier signerVerifier;
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    public static void renderJson(HttpServletResponse response, Object jsonObject) {
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            writer.write(JSONUtil.toJsonStr(jsonObject));
        } catch (IOException e) {
            // do something
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (matcher(request.getRequestURI())) {
            filterChain.doFilter(request, response);
        } else {
            try {
                String authToken = request.getHeader("Authorization");
                if (null == authToken) {
                    authToken = request.getParameter("t");
                }
                if (null == authToken) {
                    throw new ServiceException(ErrorCode.NO_API_ACCESS_POWER);
                }
                Jwt jwt = JwtHelper.decodeAndVerify(StringUtils.substringAfter(authToken, "Bearer ").trim(), signerVerifier);
                CurrentUser currentUser = JSONUtil.toBean(jwt.getClaims(), CurrentUser.class);

                UserContext.set(currentUser);
                log.info("current currentUser is : {}", null != currentUser ? currentUser.toString() : " null");
            } catch (Exception e) {
                log.error("current user filter error. url is [{}]", request.getRequestURI(), e);
                response.setStatus(401);
                response.setCharacterEncoding("utf-8");
                response.getWriter().print(e.getMessage());
            }

            filterChain.doFilter(request, response);
        }
    }

    /**
     * 匹配需要放开的路径
     *
     * @param requestUrl
     * @return
     */
    private boolean matcher(String requestUrl) {

        for (String url : urlProperties.getIgnores()) {
            if (antPathMatcher.match(url, requestUrl)) {
                return true;
            }
        }

        for (String url : urlProperties.getPublicIgnores()) {
            if (antPathMatcher.match(url, requestUrl)) {
                return true;
            }
        }

        return false;
    }
}

