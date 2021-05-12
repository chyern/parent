package com.github.chyern.permission;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/5/10
 */
public interface PermissionHandler {

    Boolean hasPermission(String permissionCode);
}
