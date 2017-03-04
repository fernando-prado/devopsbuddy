package com.devopsbuddy.test.integration;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.devopsbuddy.backend.persistence.domain.backend.Plan;
import com.devopsbuddy.backend.persistence.domain.backend.Role;
import com.devopsbuddy.backend.persistence.domain.backend.User;
import com.devopsbuddy.backend.persistence.domain.backend.UserRole;
import com.devopsbuddy.backend.persistence.repositories.PlanRepository;
import com.devopsbuddy.backend.persistence.repositories.RoleRepository;
import com.devopsbuddy.backend.persistence.repositories.UserRepository;
import com.devopsbuddy.enums.PlansEnum;
import com.devopsbuddy.enums.RolesEnum;
import com.devopsbuddy.utils.UserUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryIntegrationTest {

    @Autowired
    protected PlanRepository planRepository;

    @Autowired
    protected RoleRepository roleRepository;

    @Autowired
    protected UserRepository userRepository;
    
    @Before
    public void init() {
        Assert.assertNotNull(planRepository);
        Assert.assertNotNull(roleRepository);
        Assert.assertNotNull(userRepository);
    }
    @Test
    public void testCreateNewPlan() throws Exception {
        Plan basicPlan = createPlan(PlansEnum.BASIC);
        planRepository.save(basicPlan);
        Plan retrievedPlan = planRepository.findOne(PlansEnum.BASIC.getId());
        Assert.assertNotNull(retrievedPlan);
    }
    
    @Test
    public void testCreateNewRole() throws Exception {
        Role basicRole = createRole(RolesEnum.BASIC);
        roleRepository.save(basicRole);
        Role retrievedRole = roleRepository.findOne(RolesEnum.BASIC.getId());
        Assert.assertNotNull(retrievedRole);
    }

    @Test
    public void testCreateNewUser() throws Exception {
        Plan basicPlan = createPlan(PlansEnum.BASIC);
        planRepository.save(basicPlan);
        
        User basicUser = UserUtils.createBasicUser();
        basicUser.setPlan(basicPlan);
        
    	
    	Role basicRole = createRole(RolesEnum.BASIC);
        Set<UserRole> userRoles = new HashSet<>();
        UserRole userRole = new UserRole(basicUser, basicRole);
        userRoles.add(userRole);
        
    	basicUser.getUserRoles().addAll(userRoles);
    	
    	for (UserRole ur : userRoles) {
    	   	roleRepository.save(ur.getRole());
    	}
    	
    	basicUser = userRepository.save(basicUser);
    	User newlyCreatedUser = userRepository.findOne(basicUser.getId());
    	Assert.assertNotNull(newlyCreatedUser);
    	Assert.assertTrue(newlyCreatedUser.getId() != 0);
    	Assert.assertNotNull(newlyCreatedUser.getPlan());
    	Assert.assertNotNull(newlyCreatedUser.getPlan().getId());
    	Set<UserRole> newlyCreatedUserRoles = newlyCreatedUser.getUserRoles();
    	for (UserRole ur : newlyCreatedUserRoles) {
        	Assert.assertNotNull(ur.getRole());
        	//System.out.println(ur.getRole());
        	//System.out.println(ur.getRole().getId());
        	Assert.assertNotNull(ur.getRole().getId());
    	}
    	
    }

    
    //Private methods
    private Plan createPlan(PlansEnum plansEnum) {
    	return new Plan(plansEnum);
    }

    private Role createRole(RolesEnum rolesEnum) {
    	return new Role(rolesEnum);
    }

   
}
