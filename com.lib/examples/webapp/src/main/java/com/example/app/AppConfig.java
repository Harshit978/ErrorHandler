package com.example.app;

import com.example.errorhandler.DefaultErrorMapper;
import com.example.errorhandler.ErrorHandlingFilter;
import com.example.errorhandler.ErrorCode;
import jakarta.servlet.FilterRegistration;
import jakarta.servlet.ServletContext;

//

public class AppConfig {
    public void registerFilters(ServletContext ctx) {
        DefaultErrorMapper mapper = new DefaultErrorMapper();
        // register custom exception mappings
        mapper.registerMapping(CustomException.class, ErrorCode.RESOURCE_NOT_FOUND);

        FilterRegistration.Dynamic filter = ctx.addFilter("errorFilter",
                new ErrorHandlingFilter(mapper));
        filter.addMappingForUrlPatterns(null, false, "/*");
    }
}
