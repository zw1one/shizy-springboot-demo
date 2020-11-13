package com.shizy.common.json.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDto {

    /**
     * 当前页
     */
    private Integer page;

    /**
     * 每页的data条数
     */
    private Integer pageSize;

    public static Page getPageInstance(PageDto pageDto) {
        int page0 = (pageDto.getPage() == null || pageDto.getPage() == 0) ? 1 : pageDto.getPage();
        int pageSize0 = (pageDto.getPageSize() == null || pageDto.getPageSize() == 0) ? 10 : pageDto.getPageSize();
        return new Page(page0, pageSize0);
    }

}


























