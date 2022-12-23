package com.uidser.ppmusic.security.realconfig.tokenfilter;

import com.uidser.ppmusic.common.jjwt.JJwtUtil;
import com.uidser.ppmusic.common.r.R;
import com.uidser.ppmusic.common.resposneutil.ResponseUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

//    private RedisTemplate redisTemplate;

//    public TokenAuthenticationFilter(RedisTemplate redisTemplate) {
//        this.redisTemplate = redisTemplate;
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if("/security/login".equals(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken passwordAuthenticationToken = getAuthentication(request);
        if(passwordAuthenticationToken != null) {
            SecurityContextHolder.getContext().setAuthentication(passwordAuthenticationToken);
            filterChain.doFilter(request, response);
        } else {
            ResponseUtil.response(response, new R().error().setCode(403));
        }
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("token");
        if(!StringUtils.isEmpty(token)) {
            String username = JJwtUtil.getUserByToken(token);
            if(!StringUtils.isEmpty(username)) {
//                String o = (String) redisTemplate.opsForValue().get(username);
//                Gson gson = new Gson();
//                List<Map<String, String>> list = gson.fromJson(o, List.class);
//                List<SimpleGrantedAuthority> simpleGrantedAuthorityList = new ArrayList<>();
//                for (Map<String, String> map:list) {
//                    String s = map.get("role");
//                    simpleGrantedAuthorityList.add(new SimpleGrantedAuthority(s));
//                }
                return new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
            }
        }
        return null;
    }
}
