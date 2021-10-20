package com.yupi.father.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.father.base.BaseResponse;
import com.yupi.father.base.ResultUtils;
import com.yupi.father.exception.BusinessException;
import com.yupi.father.exception.ErrorCodeEnum;
import com.yupi.father.model.entity.Tag;
import com.yupi.father.model.request.SearchTagsRequest;
import com.yupi.father.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 标签管理
 *
 * @author liyupi
 */
@RestController
@RequestMapping("/tag")
@CrossOrigin(origins = {"http://father.cool", "https://father.cool", "http://127.0.0.1:8080"})
@Slf4j
public class TagController {

    @Resource
    private TagService tagService;

    /**
     * 插入标签
     *
     * @param tag
     * @return
     */
//    @PostMapping("/add")
//    public BaseResponse<Integer> saveTag(@RequestBody Tag tag) {
//        if (tag == null) {
//            throw new BusinessException(ErrorCodeEnum.REQUEST_PARAMS_ERROR);
//        }
//        tagService.save(tag);
//        return ResultUtils.success(tag.getId());
//    }

    /**
     * 更新标签
     *
     * @param tag
     * @return
     */
//    @PostMapping("/update")
//    public BaseResponse<Boolean> updateTag(@RequestBody Tag tag) {
//        if (tag == null || tag.getId() == null) {
//            throw new BusinessException(ErrorCodeEnum.REQUEST_PARAMS_ERROR);
//        }
//        return ResultUtils.success(tagService.updateById(tag));
//    }

    /**
     * 搜索标签
     *
     * @param tagRequest
     * @return
     */
    @GetMapping("/search")
    public BaseResponse<IPage<Tag>> searchTags(SearchTagsRequest tagRequest) {
        if (tagRequest == null) {
            throw new BusinessException(ErrorCodeEnum.REQUEST_PARAMS_ERROR);
        }
        // 封装查询请求
        QueryWrapper<Tag> tagQueryWrapper = new QueryWrapper<>();
        String name = tagRequest.getName();
        if (StringUtils.isNotBlank(name)) {
            tagQueryWrapper.like("name", name);
        }
        tagQueryWrapper.orderByDesc("createTime");
        return ResultUtils.success(tagService.page(new Page<>(tagRequest.getPageNum(), tagRequest.getPageSize()),
                tagQueryWrapper));
    }

    /**
     * 删除标签
     *
     * @param deleteRequest
     * @return
     */
//    @PostMapping("/delete")
//    public BaseResponse<Boolean> deleteTag(@RequestBody DeleteRequest deleteRequest) {
//        if (deleteRequest == null || deleteRequest.getId() < 1) {
//            throw new BusinessException(ErrorCodeEnum.REQUEST_PARAMS_ERROR);
//        }
//        return ResultUtils.success(tagService.removeById(deleteRequest.getId()));
//    }

}
