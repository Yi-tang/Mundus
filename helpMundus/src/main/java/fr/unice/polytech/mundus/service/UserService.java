package fr.unice.polytech.mundus.service;


import fr.unice.polytech.mundus.data.User;

/**
 * @author TANG Yi
 */
public class UserService {

    /*
    public boolean findLogin(User user){
        boolean flag = false;
        String sql = "select username from user where username = ?and password =?";
        try {
            this.pstmt = this.conn.prepareStatement(sql);
            this.pstmt.setString(1, user.getUsername());
            this.pstmt.setString(2, user.getPassword());
            ResultSet result = this.pstmt.executeQuery();
            if(result.next()){
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            dbutil.DBCclose();
        }
        return flag;
    }*/
    public boolean register(User user) {
        boolean flag = false;
/////////////////////
        return flag;
    }



}
