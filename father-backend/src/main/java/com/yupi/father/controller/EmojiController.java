package com.yupi.father.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.father.base.BaseResponse;
import com.yupi.father.base.ResultUtils;
import com.yupi.father.exception.BusinessException;
import com.yupi.father.exception.ErrorCodeEnum;
import com.yupi.father.manager.TencentCosManager;
import com.yupi.father.model.entity.Emoji;
import com.yupi.father.model.request.SearchEmojisRequest;
import com.yupi.father.service.EmojiService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

/**
 * 表情管理
 *
 * @author liyupi
 */
@RestController
@RequestMapping("/emoji")
@CrossOrigin(origins = {"http://father.cool", "https://father.cool", "http://127.0.0.1:8080"})
@Slf4j
public class EmojiController {

    @Resource
    private EmojiService emojiService;

    @Resource
    private TencentCosManager tencentCosManager;

    /**
     * 插入表情
     *
     * @param emoji
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Integer> saveEmoji(@RequestBody Emoji emoji) {
        if (emoji == null) {
            throw new BusinessException(ErrorCodeEnum.REQUEST_PARAMS_ERROR);
        }
        emojiService.save(emoji);
        return ResultUtils.success(emoji.getId());
    }

    /**
     * 更新表情
     *
     * @param emoji
     * @return
     */
//    @PostMapping("/update")
//    public BaseResponse<Boolean> updateEmoji(@RequestBody Emoji emoji) {
//        if (emoji == null || emoji.getId() == null) {
//            throw new BusinessException(ErrorCodeEnum.REQUEST_PARAMS_ERROR);
//        }
//        return ResultUtils.success(emojiService.updateById(emoji));
//    }

    /**
     * 审核表情
     *
     * @param emojiRequest
     * @return
     */
//    @PostMapping("/review")
//    public BaseResponse<Boolean> reviewEmoji(@RequestBody ReviewEmojiRequest emojiRequest) {
//        if (emojiRequest == null || emojiRequest.getId() <= 0 || emojiRequest.getReviewStatus() < 0) {
//            throw new BusinessException(ErrorCodeEnum.REQUEST_PARAMS_ERROR);
//        }
//        Emoji emoji = new Emoji();
//        emoji.setId(emojiRequest.getId());
//        emoji.setReviewStatus(emojiRequest.getReviewStatus());
//        return ResultUtils.success(emojiService.updateById(emoji));
//    }

    /**
     * 搜索表情
     *
     * @param emojiRequest
     * @return
     */
    @GetMapping("/search")
    public BaseResponse<IPage<Emoji>> searchEmojis(SearchEmojisRequest emojiRequest) {
        if (emojiRequest == null) {
            throw new BusinessException(ErrorCodeEnum.REQUEST_PARAMS_ERROR);
        }
        int pageNum = emojiRequest.getPageNum();
        int pageSize = emojiRequest.getPageSize();
        final int MAX_VIEW_NUM = 400;
        if (pageNum * pageSize > MAX_VIEW_NUM) {
            return ResultUtils.error(ErrorCodeEnum.REQUEST_PARAMS_ERROR.getCode(), "最多可查看 400 条数据");
        }
        // 封装查询请求
        QueryWrapper<Emoji> emojiQueryWrapper = new QueryWrapper<>();
        String name = emojiRequest.getName();
        if (StringUtils.isNotBlank(name)) {
            emojiQueryWrapper.like("name", name);
        }
        String tag = emojiRequest.getTag();
        if (StringUtils.isNotBlank(tag)) {
            emojiQueryWrapper.like("tags", tag);
        }
        if (emojiRequest.getReviewStatus() != null) {
            emojiQueryWrapper.eq("reviewStatus", emojiRequest.getReviewStatus());
        }
        emojiQueryWrapper.orderByDesc("createTime");
        return ResultUtils.success(emojiService.page(new Page<>(emojiRequest.getPageNum(), emojiRequest.getPageSize()),
                emojiQueryWrapper));
    }

//    /**
//     * 删除表情
//     *
//     * @param deleteRequest
//     * @return
//     */
//    @PostMapping("/delete")
//    public BaseResponse<Boolean> deleteEmoji(@RequestBody DeleteRequest deleteRequest) {
//        if (deleteRequest == null || deleteRequest.getId() < 1) {
//            throw new BusinessException(ErrorCodeEnum.REQUEST_PARAMS_ERROR);
//        }
//        return ResultUtils.success(emojiService.removeById(deleteRequest.getId()));
//    }

    /**
     * 上传文件
     *
     * @return
     */
    @PostMapping("/upload")
    public BaseResponse<String> uploadFile(MultipartFile file) throws IOException {
        File localFile = File.createTempFile("temp", null);
        String fileName = System.currentTimeMillis() / 1000 + "-" + file.getOriginalFilename();
        try {
            file.transferTo(localFile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(ErrorCodeEnum.FILE_UPLOAD_ERROR);
        }
        String resultPath = tencentCosManager.uploadFile(fileName, localFile);
        // 清理临时文件
        boolean deleteResult = localFile.delete();
        if (!deleteResult) {
            log.error("fileDelete error", localFile.getName());
        }
        return ResultUtils.success(resultPath);
    }

}
