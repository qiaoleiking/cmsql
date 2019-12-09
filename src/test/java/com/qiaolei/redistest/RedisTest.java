package com.qiaolei.redistest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.qiaolei.OtherUtils;
import com.qiaolei.beans.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:redis.xml")
public class RedisTest {
	
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	
	
	//3.模拟生成5万条User对象（22分）
	@Test
	public void BeansTest(){
		for (int i = 1;i <= 50000; i++) {
			User user = new User();
			user.setId(i);
			user.setName(OtherUtils.getName());
			user.setGender(OtherUtils.getSex());
			user.setPhone(OtherUtils.getPhone());
			user.setBirthday(OtherUtils.getBirthday());
			user.setEmail(OtherUtils.getMail());
			System.out.println(user);
		}	
	}
	
	/*
	 * key系列化器StringRedisSerializer。
	 * value系列化器为JdkSerializationRedisSerializer
	 */
	@Test
	public void JDKTest(){
		
		long start = System.currentTimeMillis();
		for (int i = 1;i <= 50000; i++) {
			User user = new User();
			user.setId(i);//(1)ID使用1-5万的顺序号。（2分）
			user.setName(OtherUtils.getName());//姓名使用3个随机汉字模拟
//			user.setGender(OtherUtils.getSex());/性别在女和男两个值中随机
			user.setPhone(OtherUtils.getPhone());//手机以13开头+9位随机数模拟
			user.setBirthday(OtherUtils.getBirthday());//生日要模拟18-70岁之间，即日期从1949年到2001年之间。
//			user.setEmail(OtherUtils.getMail());/邮箱以3-20个随机字母
			redisTemplate.opsForValue().set("user"+i, user);
		}	
		long end = System.currentTimeMillis();
		System.out.println("使用JDK系列化方式");
		System.out.println("共计使用"+(end-start)+"毫秒");
		System.out.println("保存数量50000条");
		
	}
	
	/**
	 *(2)key系列化器StringRedisSerializer。value系列化器为Jackson2JsonRedisSerializer
	 */
	@Test
	public void JSONTest(){
		
		long start = System.currentTimeMillis();
		for (int i = 1;i <= 50000; i++) {
			User user = new User();
			user.setId(i);
			user.setName(OtherUtils.getName());
			user.setGender(OtherUtils.getSex());
			user.setPhone(OtherUtils.getPhone());
			user.setBirthday(OtherUtils.getBirthday());
			user.setEmail(OtherUtils.getMail());
			redisTemplate.opsForValue().set("user"+i, user);
		}	
		long end = System.currentTimeMillis();
		System.out.println("使用JSON系列化方式");
		System.out.println("共计使用"+(end-start)+"毫秒");
		System.out.println("保存数量50000条");
		
	}
	
	
	/*
	 * key系列化器StringRedisSerializer。
	 * hashKey系列化器StringRedisSerializer，
	 * hashValue系列化器StringRedisSerializer
	 */
	@Test
	public void HashTest(){
		
		long start = System.currentTimeMillis();
		for (int i = 1;i <= 50000; i++) {
			User user = new User();
			user.setId(i);
			user.setName(OtherUtils.getName());
			user.setGender(OtherUtils.getSex());
			user.setPhone(OtherUtils.getPhone());
			user.setBirthday(OtherUtils.getBirthday());
			user.setEmail(OtherUtils.getMail());
			redisTemplate.opsForHash().put("users"+i, "user"+i, user.toString());
		}	
		long end = System.currentTimeMillis();
		System.out.println("使用Hash系列化方式");
		System.out.println("共计使用"+(end-start)+"毫秒");
		System.out.println("保存数量50000条");
		
	}
	
	
	
	
	
	
	
}




















