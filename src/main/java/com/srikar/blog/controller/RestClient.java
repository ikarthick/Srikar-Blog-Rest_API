package com.srikar.blog.controller;

import com.srikar.blog.entity.Activity;
import com.srikar.blog.entity.Post;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class RestClient {

    private static final String GET_ALL_USERS_API = "http://localhost:8080/api/posts";
    private static final String GET_POST_BY_ID_API = "http://localhost:8080/api/posts/{id}";
    private static final String CREATE_POST_API = "http://localhost:8080/api/posts";
    private static final String UPDATE_POST_API = "http://localhost:8080/api/posts/{id}";
    private static final String DELETE_POST_API = "http://localhost:8080/api/posts/{id}";

    static RestTemplate restTemplate = new RestTemplate();
    public static void main(String[] args) {
   callGetExternalActivityService();
   callGetCurrencyAndCodesExternalAPI();
    }

    private static void callGetAllPostsAPI(){

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

       ResponseEntity<String> result= restTemplate.exchange(GET_ALL_USERS_API, HttpMethod.GET, entity, String.class);
        System.out.println(result);
    }

    private static void callGetPostById(){
        Map<String, Integer> param = new HashMap<>();
        param.put("id", 5);

        Post post = restTemplate.getForObject(GET_POST_BY_ID_API, Post.class, param);
        assert post != null;
        System.out.println(post.getComments());
        System.out.println(post.getTitle());
        System.out.println(post.getDescription());
        System.out.println(post.getContent());
    }

    private static void callGetCurrencyAndCodesExternalAPI(){

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<String> result = restTemplate.exchange("https://api.coinbase.com/v2/currencies", HttpMethod.GET, entity, String.class);
        System.out.println(result);
    }

    private static void callGetExternalActivityService(){

        Activity activity = restTemplate.getForObject("https://www.boredapi.com/api/activity", Activity.class);
        System.out.println("Activity: "+ activity.getActivity());
        System.out.println("Accessibility: "+ activity.getAccessibility());
        System.out.println("Key: "+ activity.getKey());
        System.out.println("Participants: "+ activity.getParticipants());
        System.out.println("Price : "+ activity.getPrice());
        System.out.println("Type: "+ activity.getType());

    }

}
