package com.zk.comment.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zk.comment.entity.Comment;
import com.zk.comment.request.CommentDTO;
import com.zk.comment.request.CommentQuery;
import com.zk.comment.response.CommentVO;
import com.zk.comment.service.ICommentService;
import com.zk.common.entity.Response;
import com.zk.common.exception.ErrorCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import java.util.Objects;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author
 */
@RestController
@RequestMapping("/comments")
@Api(value = "评论", tags = "评论")
public class CommentController {
    @Resource
    private ICommentService orderTblService;

    @PostMapping
    @ApiOperation(value = "新增评论", notes = "新增评论")
    @PreAuthorize("@pm.check('test')")
    Response<Comment> save(@RequestBody CommentDTO commentDTO) {
        if (Objects.isNull(commentDTO.getArticleId())) {
            return Response.failureWithMsg("必须选择评论的文章", ErrorCode.PARAMS_ERR.code);
        }
        if (StringUtils.isBlank(commentDTO.getContent())) {
            return Response.failureWithMsg("评论内容必须", ErrorCode.PARAMS_ERR.code);
        }
        return Response.successWithData(orderTblService.create(commentDTO));
    }

    @GetMapping("tree")
    @ApiOperation(value = "返回评论-树形", notes = "返回评论，每页的全部留言及树形嵌套评论。时间倒序")
    @PermitAll
    Response<IPage<CommentVO>> tree(@RequestBody CommentQuery commentQuery) {
        if (Objects.isNull(commentQuery.getArticleId())) {
            return Response.failureWithMsg("必须选择评论的文章", ErrorCode.PARAMS_ERR.code);
        }
        return Response.successWithData(orderTblService.tree(commentQuery));
    }
}
