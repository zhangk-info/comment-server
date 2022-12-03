package com.zk.comment.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zk.comment.entity.Comment;
import com.zk.comment.mapper.CommentMapper;
import com.zk.comment.request.CommentDTO;
import com.zk.comment.request.CommentQuery;
import com.zk.comment.response.CommentVO;
import com.zk.comment.service.ICommentService;
import com.zk.common.util.TreeUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * <p>
 * 评论服务实现类
 * </p>
 *
 * @author
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {


    /**
     * 新建评论
     *
     * @param commentDTO
     * @return
     */
    public Comment create(CommentDTO commentDTO) {

        Comment comment = new Comment();
        BeanUtil.copyProperties(commentDTO, comment);
        if (Objects.isNull(comment.getPid())) {
            comment.setPid(comment.getArticleId());
        }
        this.baseMapper.insert(comment);
        return comment;
    }

    /**
     * 返回评论，每页的全部留言及树形嵌套评论。时间倒序
     *
     * @param commentQuery
     * @return
     */
    @Override
    public IPage<CommentVO> tree(CommentQuery commentQuery) {
        // 先根据条件查询出第一层数据
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<Comment>();
        queryWrapper.eq(Comment::getArticleId, commentQuery.getArticleId())
                .eq(Comment::getPid, commentQuery.getArticleId())
                .orderByDesc(Comment::getGmtCreate);
        IPage p = new Page(commentQuery.getPage(), commentQuery.getPageSize());
        IPage<Comment> firstFloorCommentPage = baseMapper.selectPage(p, queryWrapper);
        // 构建返回实体
        IPage<CommentVO> returnPage = new Page<>();
        BeanUtil.copyProperties(p, returnPage, "records");
        if (CollectionUtils.isEmpty(firstFloorCommentPage.getRecords())) {
            return returnPage;
        }
        // 得到第一层的所有ids
        List<Comment> firstFloorComments = firstFloorCommentPage.getRecords();
        List<Long> firstFloorCommentIds = firstFloorComments.stream().map(t -> t.getId()).collect(Collectors.toList());
        // 得到下级的全部留言
        LambdaQueryWrapper<Comment> childQueryWrapper = new LambdaQueryWrapper<Comment>();
        childQueryWrapper.eq(Comment::getArticleId, commentQuery.getArticleId())
                .in(Comment::getFirstFloorId, firstFloorCommentIds)
                .orderByDesc(Comment::getGmtCreate);
        List<Comment> childCommentVos = baseMapper.selectList(childQueryWrapper);
        // 构建返回的树形列表对象并将之前的所有数据放入
        List<CommentVO> returnVos = new ArrayList<>();
        List<CommentVO> returnVos1 = firstFloorComments.stream().map(this::convertVo).collect(Collectors.toList());
        List<CommentVO> returnVos2 = childCommentVos.stream().map(this::convertVo).collect(Collectors.toList());
        returnVos.addAll(returnVos1);
        returnVos.addAll(returnVos2);
        // 将原数据转为树形结构并设置返回值 TreeUtils.listToTree()
        returnPage.setRecords(TreeUtils.listToTree(returnVos));
        return returnPage;
    }

    /**
     * 类型转换成vo
     *
     * @param comment
     * @return
     */
    public CommentVO convertVo(Comment comment) {
        CommentVO vo = new CommentVO();
        BeanUtil.copyProperties(comment, vo);
        vo.setParentId(vo.getPid().toString());
        return vo;
    }
}
