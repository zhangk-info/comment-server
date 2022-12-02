package com.zk.comment.response;

import com.zk.common.entity.BaseTree;
import com.zk.common.entity.TreeNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@ApiModel("评论添加或修改实体")
@Data
@EqualsAndHashCode(callSuper = true)
public class CommentVO extends TreeNode<CommentVO> {

    /**
     * '文章id'
     */
    @ApiModelProperty("文章id")
    private Long articleId;

    /**
     * 上级评论id,没有就是文章id
     */
    @ApiModelProperty("上级评论id,没有就不传入，后台默认为文章id")
    private Long pid;

    /**
     * '评论内容'
     */
    @ApiModelProperty("文章内容")
    private String content;

    @ApiModelProperty("创建时间")
    private Date gmtCreate;

    @ApiModelProperty("更新时间")
    private Date gmtUpdate;

    @ApiModelProperty("创建人姓名")
    private String createUser;

    @ApiModelProperty("更新人姓名")
    private String updateUser;
}
