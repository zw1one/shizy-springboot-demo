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
        return getPageInstance(pageDto, 1, 10);
    }

    public static Page getPageInstance(PageDto pageDto, Integer defaultPage, Integer defaultPageSize) {
        return new Page(
                (pageDto.getPage() == null || pageDto.getPage() == 0) ? defaultPage : pageDto.getPage(),//有值取值，无值取默认
                (pageDto.getPageSize() == null || pageDto.getPageSize() == 0) ? defaultPageSize : pageDto.getPageSize()//有值取值，无值取默认
        );
    }

}


























