package model;

public class Comment {
    private int commentId;
    private String commentText;
    private int commentUserId;
    private int commentProductId;
    private String commentUserName;

    public Comment(String commentText, int commentUserId, int commentProductId, String commentUserName) {
        this.commentText = commentText;
        this.commentUserId = commentUserId;
        this.commentProductId = commentProductId;
        this.commentUserName = commentUserName;
    }

    public Comment(String commentText, int commentUserId, int commentProductId) {
        this.commentText = commentText;
        this.commentUserId = commentUserId;
        this.commentProductId = commentProductId;
    }

    public String getCommentUserName() {
        return commentUserName;
    }

    public void setCommentUserName(String commentUserName) {
        this.commentUserName = commentUserName;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }


    public int getCommentUserId() {
        return commentUserId;
    }

    public void setCommentUserId(int commentUserId) {
        this.commentUserId = commentUserId;
    }

    public int getCommentProductId() {
        return commentProductId;
    }

    public void setCommentProductId(int commentProductId) {
        this.commentProductId = commentProductId;
    }
}
