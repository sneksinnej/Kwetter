/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import domain.Role;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 *
 * @author Dennis
 */
@Named("roles")
@ApplicationScoped
public class RoleBean {

    public Role[] getRoles() {
        return Role.values();
    }
}

