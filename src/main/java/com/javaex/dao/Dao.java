package com.javaex.dao;

import java.sql.SQLException;

public interface Dao<T> {

    DaoResult insert(T t) throws SQLException;
    DaoResult readBy(String condition, T t) throws SQLException;
    DaoResult update(T t) throws SQLException;
    DaoResult delete(T t);




    /**
     *
     *
     * 	public List<BoardVo> getList();  // 게시물 전체 목록 조회
     * 	public BoardVo getBoard(int no); // 게시물 상세 조회
     * 	public int insert(BoardVo vo);   // 게시물 등록
     * 	public int delete(int no);       // 게시물 삭제
     * 	public int update(BoardVo vo);   // 게시물 수정
     *
     *
     *
     * 		public List<GuestbookVo> getList(); // 방명록 목록 조회
     * 	public int insert(GuestbookVo vo);  // 방명록 정보 등록
     * 	public int delete(GuestbookVo vo);  // 방명록 정보 삭제
     *
     *
     *
     *   public int update(UserVo vo); // 회원수정
     * 	public int insert(UserVo vo); // 회원가입
     * 	public UserVo getUser(String email, String password); // 정보가 맞는 회원정보를 리턴
     * 	public UserVo getUser(int no); // 정보가 맞는 회원정보를 리턴
     *   public String idCheck(String email); // // 정보가 맞는 회원정보를 리턴
     */

}
