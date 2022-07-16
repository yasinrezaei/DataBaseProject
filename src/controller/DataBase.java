package controller;

import model.*;

import java.sql.*;
import java.util.ArrayList;

public class DataBase {
    private static Connection connection;
    private static Statement statement;

    private DataBase(){}

    public static void makeConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gigikala","yasin1","1190272131");
        statement = connection.createStatement();
    }
    public static void closeConnection() throws SQLException {
        if(connection!=null){
            statement.close();
            connection.close();
        }
    }

    public static int createProduct(Product product) throws SQLException {
        makeConnection();
        statement.execute(String.format("insert into products (product_name,product_price,product_category) values ('%s','%d','%d')",product.getProductName(),product.getProductPrice(),product.getCategoryId()),Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = statement.getGeneratedKeys();
        rs.next();//go to first row
        int id = rs.getInt(1);
        closeConnection();
        return id;
    }

    public static void updateProduct(Product product) throws SQLException {
        makeConnection();
        PreparedStatement statement = connection.prepareStatement("UPDATE products SET product_name = ? , product_price = ?, product_category = ? WHERE product_id=?; ");

        statement.setString(1, String.valueOf(product.getProductName()));
        statement.setInt(2, product.getProductPrice());
        statement.setInt(3, product.getCategoryId());
        statement.setInt(4, product.getProductId());
        statement.executeUpdate();

        closeConnection();
    }

    public static ArrayList<Product> getAllProducts() throws SQLException {
        makeConnection();
        ResultSet rs = statement.executeQuery("select * from products");
        ArrayList<Product> products = new ArrayList<>();
        while (rs.next()){
            products.add(new Product(rs.getInt("product_id"),rs.getString("product_name"),rs.getInt("product_price"),rs.getInt("product_category")));
        }
        closeConnection();
        return products;
    }

    //sending methods

    public static ArrayList<SendingMethod> getAllSendingMethods() throws SQLException {
        makeConnection();
        ResultSet rs = statement.executeQuery("select * from sendingmethods");
        ArrayList<SendingMethod> sendingMethods = new ArrayList<>();
        while (rs.next()){
            sendingMethods.add(new SendingMethod(rs.getInt("sendingmethod_id"),rs.getString("sendingmethod_name")));
        }
        closeConnection();
        return sendingMethods;
    }







    public static ArrayList<Product> getSuggestedProducts(int price,int categoryId) throws SQLException {
        makeConnection();
        PreparedStatement statement = connection.prepareStatement("select * from products where product_category = ? AND abs(product_price-?)<=10 ");
        statement.setInt(1, categoryId);
        statement.setInt(2, price);
        ResultSet rs = statement.executeQuery();
        ArrayList<Product> products = new ArrayList<>();
        while (rs.next()){
            products.add(new Product(rs.getInt("product_id"),rs.getString("product_name"),rs.getInt("product_price"),rs.getInt("product_category")));
        }
        closeConnection();
        return products;
    }
    public static Product getProductDetail(int productId) throws SQLException {
        makeConnection();
        PreparedStatement statement = connection.prepareStatement("select * from products where product_id = ?");
        statement.setString(1, String.valueOf(productId));
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();//go to first row
        Product product =new Product(resultSet.getInt("product_id"),resultSet.getString("product_name"),resultSet.getInt("product_price"),resultSet.getInt("product_category"));
        closeConnection();
        return product;
    }

    public static int createUser(String username,String password,int is_admin) throws SQLException {
        makeConnection();
        statement.execute(String.format("insert into user (username,password,is_admin) values ('%s','%s','%d')",username,password,is_admin),Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = statement.getGeneratedKeys();
        rs.next();//go to first row
        int user_id = rs.getInt(1);
        closeConnection();
        return user_id;

    }
    //ticket
    public static int createTicket(int orderId) throws SQLException {

        makeConnection();
        statement.execute(String.format("insert into tickets (ticket_order) values ('%d')",orderId),Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = statement.getGeneratedKeys();
        rs.next();//go to first row
        int ticket_id = -1;
        ticket_id = rs.getInt(1);
        closeConnection();
        return ticket_id;
    }

    public static ArrayList<Ticket> getUserTickets(int userId) throws SQLException {
        makeConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT tickets.ticket_id,orders.order_id ,orders.order_user,orders.order_date,user.username FROM tickets ,orders,user  where orders.order_id = tickets.ticket_order and orders.order_user = ? and orders.order_user = user.user_id;");
        statement.setString(1, String.valueOf(userId));
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Ticket> tickets = new ArrayList<>();
        while (resultSet.next()){
            tickets.add(new Ticket(resultSet.getInt("ticket_id"),resultSet.getInt("order_id"),resultSet.getString("username"),resultSet.getString("order_date")));
        }
        closeConnection();
        return tickets;
    }
    public static ArrayList<Ticket> getAllTickets() throws SQLException {
        makeConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT tickets.ticket_id,orders.order_id ,orders.order_user,orders.order_date,user.username FROM tickets ,orders,user  where orders.order_id = tickets.ticket_order  and orders.order_user = user.user_id;");
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Ticket> tickets = new ArrayList<>();
        while (resultSet.next()){
            tickets.add(new Ticket(resultSet.getInt("ticket_id"),resultSet.getInt("order_id"),resultSet.getString("username"),resultSet.getString("order_date")));
        }
        closeConnection();
        return tickets;
    }



    //shopping_cart
    public static int getShoppingCartId(int userId) throws SQLException {
        makeConnection();
        PreparedStatement statement = connection.prepareStatement("select * from shoppingcarts where shoppingcart_user = ?");
        statement.setString(1, String.valueOf(userId));
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();//go to first row
        int shoppingCartId = resultSet.getInt("shoppingcart_id");
        closeConnection();
        return shoppingCartId;
    }

    //create shopping cart
    public static void createShoppingCart(int userId) throws SQLException {

        makeConnection();
        statement.execute(String.format("insert into shoppingcarts (shoppingcart_user) values ('%d')",userId),Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = statement.getGeneratedKeys();
        rs.next();//go to first row
        closeConnection();

    }


    //create status
    public static void createStatus(String name) throws SQLException {

        makeConnection();
        statement.execute(String.format("insert into orderstatuses (orderstatus_name) values ('%s')",name),Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = statement.getGeneratedKeys();
        closeConnection();

    }
    //create sending method
    public static void createSendingMethod(String name) throws SQLException {

        makeConnection();
        statement.execute(String.format("insert into sendingmethods (sendingmethod_name) values ('%s')",name),Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = statement.getGeneratedKeys();
        closeConnection();

    }

    public static ArrayList<Cart> getCarts(int shoppingCartId) throws SQLException {
        makeConnection();
        PreparedStatement statement = connection.prepareStatement("select * from cartitems where shoppingcart_id = ?");
        statement.setString(1, String.valueOf(shoppingCartId));
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Cart> carts = new ArrayList<>();
        while (resultSet.next()){
            carts.add(new Cart(resultSet.getInt("cart_id"),resultSet.getInt("shoppingcart_id"),resultSet.getInt("product_id"),resultSet.getInt("product_quantity")));
        }
        closeConnection();
        return carts;
    }
    //add to shopping cart
    public static int addToShoppingCart(Cart cart) throws SQLException {
        int id=-1;
        makeConnection();
        statement.execute(String.format("insert into cartitems (shoppingcart_id,product_id,product_quantity) values ('%s','%d','%d')",cart.getShoppingCartId(),cart.getProductId(),cart.getProductQuantity()),Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = statement.getGeneratedKeys();
        rs.next();//go to first row
        id = rs.getInt(1);
        closeConnection();
        return id;
    }

    //delete cart from shopping cart
    public static void deleteCart(int cartId) throws SQLException {
        makeConnection();
        statement.execute(String.format("delete from cartitems where cart_id=%d",cartId));
        closeConnection();
    }


    //last orders
    public static ArrayList<Order> getAllOrders(int userId) throws SQLException {
        makeConnection();
        PreparedStatement statement = connection.prepareStatement("select * from orders where order_user = ?");
        statement.setString(1, String.valueOf(userId));
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Order> orders = new ArrayList<>();
        while (resultSet.next()){
            orders.add(new Order(resultSet.getInt("order_id"),resultSet.getString("order_date"),resultSet.getInt("order_status")));
        }
        closeConnection();
        return orders;
    }
    public static ArrayList<Order> getAllOrders() throws SQLException {
        makeConnection();
        PreparedStatement statement = connection.prepareStatement("select * from orders ");
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Order> orders = new ArrayList<>();
        while (resultSet.next()){
            orders.add(new Order(resultSet.getInt("order_id"),resultSet.getString("order_date"),resultSet.getInt("order_status")));
        }
        closeConnection();
        return orders;
    }


    //status
    public static ArrayList<Status> getAllStatuses() throws SQLException {
        makeConnection();
        ResultSet rs = statement.executeQuery("select * from orderstatuses");
        ArrayList<Status> statuses = new ArrayList<>();
        while (rs.next()){
            statuses.add(new Status(rs.getInt("orderstatus_id"),rs.getString("orderstatus_name")));
        }
        closeConnection();
        return statuses;
    }

    public static String getStatusDetail(int id) throws SQLException {
        makeConnection();
        PreparedStatement statement = connection.prepareStatement("select * from orderstatuses where orderstatus_id = ?");
        statement.setString(1, String.valueOf(id));
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();//go to first row
        String statusName = resultSet.getString("orderstatus_name");
        closeConnection();
        return statusName;
    }

    //ticket chats
    public static ArrayList<Chat> getTicketChats(int TicketId) throws SQLException {
        makeConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM gigikala.messages ,gigikala.user where user.user_id = messages.message_user and message_ticket = ?");
        statement.setString(1, String.valueOf(TicketId));
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Chat> chats = new ArrayList<>();
        while (resultSet.next()){
            chats.add(new Chat(resultSet.getInt("message_id"),resultSet.getInt("message_ticket"),resultSet.getString("message_text"),resultSet.getString("username")));
        }
        closeConnection();
        return chats;
    }

    //create chat
    public static int createChat(String text,int ticketId,int userId) throws SQLException {
        makeConnection();
        statement.execute(String.format("insert into messages (message_ticket,message_text,message_user) values ('%d','%s','%d')",ticketId,text,userId),Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = statement.getGeneratedKeys();
        rs.next();//go to first row
        int chatId = -1;
        chatId = rs.getInt(1);
        closeConnection();
        return chatId;
    }

    //update order

    public static void updateOrderStatus(int orderId,int statusId) throws SQLException {
        makeConnection();
        PreparedStatement statement = connection.prepareStatement("UPDATE orders SET order_status = ? WHERE order_id=?; ");

        statement.setInt(1, statusId);
        statement.setInt(2, orderId);
        statement.executeUpdate();

        closeConnection();
    }






    //caretgory
    public static ArrayList<Category> getAllCategories() throws SQLException {
        makeConnection();
        ResultSet rs = statement.executeQuery("select * from categories");
        ArrayList<Category> categories = new ArrayList<>();
        while (rs.next()){
            categories.add(new Category(rs.getInt("category_id"),rs.getString("category_name")));
        }
        closeConnection();
        return categories;
    }

    public static String getCategoryDetail(int id) throws SQLException {
        makeConnection();
        PreparedStatement statement = connection.prepareStatement("select * from categories where category_id = ?");
        statement.setString(1, String.valueOf(id));
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();//go to first row
        String categoryName = resultSet.getString("category_name");
        closeConnection();
        return categoryName;
    }



    //profile
    public static Profile getProfile(int userId) throws SQLException {
        makeConnection();
        PreparedStatement statement = connection.prepareStatement("select * from profiles where profile_user = ?");
        statement.setString(1, String.valueOf(userId));
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();//go to first row
        Profile profile =new Profile(resultSet.getInt("profile_id"),resultSet.getInt("profile_user"),resultSet.getString("name"),resultSet.getString("last_name"),resultSet.getString("phone_number"));

        closeConnection();
        return profile;
    }

    public static void updateProfile(Profile profile) throws SQLException {
        makeConnection();
        PreparedStatement statement = connection.prepareStatement("UPDATE profiles SET name = ? , last_name = ?, phone_number = ? WHERE profile_user=?; ");

        statement.setString(1, String.valueOf(profile.getName()));
        statement.setString(2, profile.getLastName());
        statement.setString(3, profile.getPhoneNumber());
        statement.setInt(4, profile.getUserId());
        statement.executeUpdate();

        closeConnection();
    }

    //create profile
    public static void createProfile(int userId) throws SQLException {
        makeConnection();
        statement.execute(String.format("insert into profiles (name,last_name,phone_number,profile_user) values ('%s','%s','%s','%d')"," "," "," ",userId),Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = statement.getGeneratedKeys();
        closeConnection();

    }




    //Addresses
    public static ArrayList<Address> getUserAllAddresses(int userId) throws SQLException {
        makeConnection();
        PreparedStatement statement = connection.prepareStatement("select * from addresses where address_user = ?");
        statement.setString(1, String.valueOf(userId));
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Address> addresses = new ArrayList<>();
        while (resultSet.next()){
            addresses.add(new Address(resultSet.getInt("address_id"),resultSet.getString("address_text"),resultSet.getInt("address_user")));
        }
        closeConnection();
        return addresses;
    }
    public static void createAddress(String addressText,int addressUser) throws SQLException {
        makeConnection();
        statement.execute(String.format("insert into addresses (address_text,address_user) values ('%s','%d')",addressText,addressUser),Statement.RETURN_GENERATED_KEYS);
        closeConnection();
    }

   //comments
    public static ArrayList<Comment> getProductComments(int productId) throws SQLException {
        makeConnection();
        PreparedStatement statement = connection.prepareStatement("select * from comments,profiles where comment_product = ? AND (comment_user=profile_user)");
        statement.setString(1, String.valueOf(productId));
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Comment> comments = new ArrayList<>();
        while (resultSet.next()){
            comments.add(new Comment(resultSet.getString("comment_text"),resultSet.getInt("comment_user"),resultSet.getInt("comment_product"),resultSet.getString("name")+" "+resultSet.getString("last_name")));
        }
        closeConnection();
        return comments;
    }
    public static int createComment(Comment comment) throws SQLException {
        int id=-1;
        makeConnection();
        statement.execute(String.format("insert into comments (comment_text,comment_user,comment_product,comment_like_cont,comment_dislike_count) values ('%s','%d','%d','%d','%d')",comment.getCommentText(),comment.getCommentUserId(),comment.getCommentProductId(),0,0),Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = statement.getGeneratedKeys();
        rs.next();//go to first row
        id = rs.getInt(1);
        closeConnection();
        return id;
    }

    //Users
    public static User userAuth(String userName,String password) throws SQLException {
        int userId=-1;
        makeConnection();
        PreparedStatement statement = connection.prepareStatement("select * from user where username = ? AND password= ?");
        statement.setString(1, String.valueOf(userName));
        statement.setString(2, String.valueOf(password));
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        User user =new User(resultSet.getInt("user_id"),resultSet.getString("username"),resultSet.getInt("is_admin"));
        closeConnection();
        return user;
    }


    //user all finished-order items
    public static ArrayList<OrderItem> getAllFinishedOrderItems(int userId) throws SQLException {
        makeConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT orders.order_id,orderitems.product_id  FROM orders,orderitems where orders.order_id = orderitems.order_id and orders.is_finished=1 and orders.order_user=?;");
        statement.setInt(1,userId);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<OrderItem> orderItems = new ArrayList<>();
        while (resultSet.next()){

            orderItems.add(new OrderItem(resultSet.getInt("product_id")));
        }

        closeConnection();
        return orderItems;

    }


    //create order
    public static int createOrder(Order order) throws SQLException {
        makeConnection();
        statement.execute(String.format("insert into orders (order_user, order_date, order_address,order_status, order_sending_method,is_finished) values ('%d','%s','%d','%d','%d','%d')",order.getOrderUserId(),order.getOrderDate(),order.getOrderAddress(),order.getOrderStatus(),order.getOrderSendingMethod(),order.getFinished()),Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = statement.getGeneratedKeys();
        rs.next();//go to first row
        int id = rs.getInt(1);
        closeConnection();
        return id;
    }

    //
    public static int createOrderItem(OrderItem orderItem) throws SQLException {
        makeConnection();
        statement.execute(String.format("insert into orderitems (order_id, product_id,product_quantity) values ('%d','%d','%d')",orderItem.getOredrId(),orderItem.getProductId(),orderItem.getProductQuantity()),Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = statement.getGeneratedKeys();
        rs.next();//go to first row
        int id = rs.getInt(1);
        closeConnection();
        return id;
    }

    //user all order orderitems
    public static ArrayList<OrderItem> getAllOrderOrderItems(int orderId) throws SQLException {
        makeConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT *  FROM orderitems,products where orderitems.product_id = products.product_id and order_id = ? ;");
        statement.setInt(1,orderId);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<OrderItem> orderItems = new ArrayList<>();
        while (resultSet.next()){

            orderItems.add(new OrderItem(resultSet.getInt("product_quantity"),resultSet.getString("product_name")));
        }

        closeConnection();
        return orderItems;

    }








}
