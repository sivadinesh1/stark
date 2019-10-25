package com.squapl.stark.service.serviceimpl;

import com.squapl.stark.model.RawUser;
import com.squapl.stark.model.User;
import com.squapl.stark.model.security.UserRole;
import com.squapl.stark.repository.RoleDao;
import com.squapl.stark.repository.UserDao;
import com.squapl.stark.service.UserService;
import com.squapl.stark.util.Helper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Set;


@Service
@Transactional
@Slf4j
public class UserImpl implements UserService {
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    Helper helper;


    @Autowired
    UserDao userDao;

    @Autowired
    RoleDao roleDao;


    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User createUser(User user, Set<UserRole> userRoles) {
        User localUser = userDao.findByUsername(user.getUsername());
        System.out.println("1111");
        if (localUser != null) {
            log.info("User with username {} already exist. Nothing will be done. ", user.getUsername());
        } else {
            String encryptedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encryptedPassword);

            System.out.println("222222");

            // DND
//            for (UserRole ur : userRoles) {
//                System.out.println("33333");
//                roleDao.save(ur.getRole());
//            }
            System.out.println("444444");
            user.getUserRoles().addAll(userRoles);
            System.out.println("555555");
            localUser = userDao.save(user);
        }

        return localUser;
    }


    public RawUser getAllMemberServices(String userid) {
        RawUser rawUser = null;
        System.out.println("usenae " + userid);
        try {
            Query query = entityManager.createNativeQuery(
                    " select * from " +
                            " member_services where userid = ? ", RawUser.class);

            //   query.setParameter(1, username);

            rawUser = (RawUser) query.getSingleResult();

        } catch (NoResultException nre) {

            return rawUser;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in checkIfUserExistsinRaw " + e.getMessage());
        }
        return rawUser;
    }

    public User getUserDetails(String username) {

        User user = null;
        System.out.println("usenae " + username);
        try {
            Query query = entityManager.createNativeQuery(
                    " select * from " +
                            " users where username = ? ", User.class);

            query.setParameter(1, username);

            user = (User) query.getSingleResult();

        } catch (NoResultException nre) {

            return user;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in checkIfUserExistsinRaw " + e.getMessage());
        }
        return user;

    }

    public int updateUserVerified(String username) {
        int rowcount = 0;
        try {
            Query query0 = entityManager.createNativeQuery(" update users set verified = 'Y' where username = ? ");
            query0.setParameter(1, username);
            rowcount = query0.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in deleteYoUserTbl " + e.getMessage());
        }

        return rowcount;
    }

    public User assignRoleValues(UserDetails userDetails, User user) {

        if (Helper.hasRole("SUPER_ADMIN", userDetails)) {
            user.setRole("superadmin");
        } else if (Helper.hasRole("CENTER_ADMIN", userDetails)) {
            user.setRole("centeradmin");
        } else if (Helper.hasRole("CORPORATE", userDetails)) {
            user.setRole("corporate");
        } else if (Helper.hasRole("TRAINER", userDetails)) {
            user.setRole("trainer");
        } else if (Helper.hasRole("MEMBER", userDetails)) {
            user.setRole("member");
        } else if (Helper.hasRole("MEMBER_COORDINATOR", userDetails)) {
            user.setRole("membercoordinator");
        }

        return user;
    }


    public int insertMember_services(String userid, Long service_id, String start_date, String end_date) {
        System.out.println("inside add Yo User");
        int rowcount = 0;


        System.out.println("startdate " + start_date);


        try {

            Query query = entityManager.createNativeQuery(" insert into member_services (userid, service_id, isactive, " +
                    " startdate, enddate) " +
                    " values (?, ?, ?, ?, ?) ");

            query.setParameter(1, Long.valueOf(userid));
            query.setParameter(2, service_id);
            query.setParameter(3, 'Y');
            query.setParameter(4, helper.dateConversionStr2DtDDMMYYYY(start_date));
            query.setParameter(5, helper.dateConversionStr2DtDDMMYYYY(end_date));


            rowcount = query.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in updateMigrationStatus " + e.getMessage());
        }

        return rowcount;
    }


}




