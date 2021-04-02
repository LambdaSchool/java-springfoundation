package com.lambdaschool.foundation.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The entity allowing interaction with the users table
 */
@Entity
@Table(name = "users")
public class User
    extends Auditable
{
    /**
     * The primary key (long) of the users table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userid;

    /**
     * The username (String). Cannot be null and must be unique
     */
    @Column(nullable = false,
        unique = true)
    private String username;

    /**
     * The password (String) for this user. Cannot be null. Never get displayed
     */

    /**
     * Primary email account of user. Could be used as the userid. Cannot be null and must be unique.
     */


    @Column(nullable = false,unique = true)
    private String firstname;

    @Column(nullable = false,unique=true)
    private String lastname;
    
    @Column(nullable = false,
            unique = true)
    @Email
    private String email;
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    /**
     * A list of emails for this user


    /**
     * Part of the join relationship between user and role
     * connects users to the user role combination
     */
    @OneToMany(mappedBy = "user",
        cascade = CascadeType.ALL,
        orphanRemoval = true)
    @JsonIgnoreProperties(value = "user",
        allowSetters = true)
    private Set<UserRoles> roles = new HashSet<>();


    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnoreProperties(value = "user", allowSetters = true)
    private Set<UserProduct> products = new HashSet<>();

    /**
     * Default constructor used primarily by the JPA.
     */
    public User()
    {
    }

    public User( String username,String firstname, String lastname, @Email String email, String password) {
        this.setUsername(username);
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.setPassword(password);

    }



    /**
     * Given the params, create a new user object
     * <p>
     * userid is autogenerated
     *
     * @param username     The username (String) of the user
     * @param password     The password (String) of the user
     * @param primaryemail The primary email (String) of the user


    /**
     * Getter for userid
     *
     * @return the userid (long) of the user
     */

    /**
     * Setter for userid. Used primary for seeding data
     *
     * @param userid the new userid (long) of the user
     */
    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public Set<UserProduct> getProducts() {
        return products;
    }

    public void setProducts(Set<UserProduct> products) {
        this.products = products;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
/**
     * Getter for username
     *
     * @return the username (String) lowercase

    /**
     * setter for username
     *
     * @param username the new username (String) converted to lowercase
     */

    /**
     * getter for primary email
     *
     * @return the primary email (String) for the user converted to lowercase
     */

    /**
     * setter for primary email
     *
     * @param primaryemail the new primary email (String) for the user converted to lowercase
     */

    /**
     * Getter for the password
     *
     * @return the password (String) of the user
     */

    /**
     * Setter for password to be used internally, after the password has already been encrypted
     *
     * @param password the new password (String) for the user. Comes in encrypted and stays that way
     */
    public void setPasswordNoEncrypt(String password)
    {
        this.password = password;
    }

    /**
     * @param password the new password (String) for this user. Comes in plain text and goes out encrypted
     */
    public void setPassword(String password)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

/**
     * Getter for the list of useremails for this user
     *
     * @return the list of useremails (List(Useremail)) for this user
     */

    /**
     * Setter for list of useremails for this user
     *
     * @param useremails the new list of useremails (List(Useremail)) for this user
     */

    /**
     * Getter for user role combinations
     *
     * @return A list of user role combinations associated with this user
     */
    public Set<UserRoles> getRoles()
    {
        return roles;
    }

    /**
     * Setter for user role combinations
     *
     * @param roles Change the list of user role combinations associated with this user to this one
     */
    public void setRoles(Set<UserRoles> roles)
    {
        this.roles = roles;
    }

    /**
     * Internally, user security requires a list of authorities, roles, that the user has. This method is a simple way to provide those.
     * Note that SimpleGrantedAuthority requests the format ROLE_role name all in capital letters!
     *
     * @return The list of authorities, roles, this user object has
     */
    @JsonIgnore
    public List<SimpleGrantedAuthority> getAuthority()
    {
        List<SimpleGrantedAuthority> rtnList = new ArrayList<>();

        for (UserRoles r : this.roles)
        {
            String myRole = "ROLE_" + r.getRole()
                .getName()
                .toUpperCase();
            rtnList.add(new SimpleGrantedAuthority(myRole));
        }

        return rtnList;
    }
}
