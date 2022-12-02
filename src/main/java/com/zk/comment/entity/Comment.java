package com.zk.comment.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zk.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_comment")
@ApiModel(value = "Comment对象", description = "")
public class Comment extends BaseEntity {


    private static final long serialVersionUID = 1L;

    /**
     * '文章id'
     */
    private Long articleId;

    /**
     * 上级评论id,没有就是文章id
     */
    private Long pid;

    /**
     * 第一层评论的id
     */
    private Long firstFloorId;

    /**
     * '评论内容'
     */
    private String content;
}
