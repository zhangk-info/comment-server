package com.zk.comment.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zk.comment.entity.Comment;
import com.zk.comment.request.CommentDTO;
import com.zk.comment.request.CommentQuery;
import com.zk.comment.response.CommentVO;

import java.util.List;

/**
 * <p>
 * 评论服务类
 * </p>
 *
 * @author
 */
public interface ICommentService extends IService<Comment> {

    /**
     * 创建评论
     */
    Comment create(CommentDTO commentDTO);

    /**
     * 返回评论，每页的全部留言及树形嵌套评论。时间倒序
     * @param commentQuery
     * @return
     */
    IPage<CommentVO> tree(CommentQuery commentQuery);
}
