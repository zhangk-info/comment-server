package com.zk.commnt;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import com.zk.CommentApplication;
import com.zk.comment.request.CommentDTO;
import com.zk.comment.request.CommentQuery;
import com.zk.common.ControllerTest;
import com.zk.common.RequestTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashMap;
import java.util.Map;

/**
 * 评论测试
 */
@ContextConfiguration(classes = CommentApplication.class)
@Slf4j
public class CommentControllerTest extends ControllerTest {

    private final String URI = "/";

    public Long saveComment(Long firstId, Long pid, int i, HttpHeaders headers) throws Exception {
        CommentDTO dto = new CommentDTO();
        dto.setArticleId(2L);
        dto.setContent("第" + i + "条评论");
        dto.setPid(pid);
        dto.setFirstFloorId(firstId);
        Map<String, Object> params = new HashMap<>();
        params.putAll(BeanUtil.beanToMap(dto));
        JSONObject dtoResponse2 = this.execute(RequestTypeEnum.POST, URI + "comments", headers, params);
        pid = dtoResponse2.getJSONObject("data").getLong("id");
        return pid;
    }

    @Test
    public void testCommentSaveAndGetAsTree() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic emhhbmdrOnpoYW5nay4uMTIz");
        Map<String, Object> params = new HashMap<>();
        params.put("grant_type", "password");
        params.put("username", "test");
        params.put("password", "b0db32e2eb00b9644dcf63def75c1ac4");

        JSONObject jsonObject = this.execute(RequestTypeEnum.POST, URI + "oauth/token", headers, params);
        String token = jsonObject.getStr("access_token");
        // 设置得到的token请求头
        headers.set("Authorization", "Bearer " + token);
        // 放入50条评论
        for (int i = 0; i < 5; i++) {
            CommentDTO dto = new CommentDTO();
            dto.setArticleId(2L);
            dto.setContent("第" + i + "条评论");
            params = new HashMap<>();
            params.putAll(BeanUtil.beanToMap(dto));
            JSONObject dtoResponse = this.execute(RequestTypeEnum.POST, URI + "comments", headers, params);
            Long firstId = dtoResponse.getJSONObject("data").getLong("id");
            Long pid = firstId;
            // 第2层 2条
            for (int j = 0; j < 2; j++) {
                Long pidj = saveComment(firstId, pid, j, headers);
                // 第3层 3条
                for (int k = 0; k < 2; k++) {
                    Long pidk = saveComment(firstId, pidj, k, headers);
                    // 第4层 4条
                    for (int l = 0; l < 2; l++) {
                        Long pidl = saveComment(firstId, pidk, l, headers);
                        // 第5层 5条
                        for (int m = 0; m < 2; m++) {
                            Long pidm = saveComment(firstId, pidl, m, headers);
                            // 第6层 6条
                            for (int n = 0; n < 2; n++) {
                                saveComment(firstId, pidm, n, headers);
                            }
                        }
                    }
                }
            }
        }

        // 得到树形结构返回值
        CommentQuery dto = new CommentQuery();
        dto.setArticleId(2L);
        params = new HashMap<>();
        params.putAll(BeanUtil.beanToMap(dto));
        JSONObject dtoResponse = this.execute(RequestTypeEnum.GET, URI + "comments/tree", headers, params);
        log.info("得到树形结构返回值为：" + dtoResponse.toString());
    }


    @Test
    public void testCommentGetAsTree() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic emhhbmdrOnpoYW5nay4uMTIz");
        Map<String, Object> params = new HashMap<>();
        params.put("grant_type", "password");
        params.put("username", "test");
        params.put("password", "b0db32e2eb00b9644dcf63def75c1ac4");

        JSONObject jsonObject = this.execute(RequestTypeEnum.POST, URI + "oauth/token", headers, params);
        String token = jsonObject.getStr("access_token");
        // 设置得到的token请求头
        headers.set("Authorization", "Bearer " + token);

        // 得到树形结构返回值
        CommentQuery dto = new CommentQuery();
        dto.setArticleId(2L);
        params = new HashMap<>();
        params.putAll(BeanUtil.beanToMap(dto));
        JSONObject dtoResponse = this.execute(RequestTypeEnum.GET, URI + "comments/tree", headers, params);
        log.info("得到树形结构返回值为：\n\n\n" + dtoResponse.toString()+"\n\n\n");
    }
}