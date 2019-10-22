package com.guigu.bookstore.dao;

import java.util.List;

/**
 * Dao �ӿ�, ���� Dao �Ļ�������, �� BaseDao �ṩʵ��.
 * @author Administrator
 *
 * @param <T>  Dao ʵ�ʲ����ķ�������
 */
public interface Dao<T> {
	/**
	 * ִ��insert ���������ز����ļ�¼��ID
	 * @param sql ��ִ�е�SQL���
	 * @param args ���ռλ���Ŀɱ����
	 * @return �����¼�¼��ID
	 */
	long insert(String sql,Object...args);

	/**
	 * ִ��update����������insert(��û�з���ֵ)��update,delete
	 * @param sql ��ִ�е�SQL���
	 * @param args ���ռλ���Ŀɱ����
	 */
	void update(String sql,Object...args );

	/**
	 * ִ�е�����¼�Ĳ�ѯ�������������¼��Ӧ�����һ������
	 * @param sql ��ִ�е�SQL���
	 * @param args ���ռλ���Ŀɱ����
	 * @return ���¼��Ӧ�����һ������
	 */
	T query(String sql,Object...args );
	
	/**
	 * ִ�ж�����¼�Ĳ�ѯ�������������¼��Ӧ�����һ��List
	 * @param sql ��ִ�е�SQL���
	 * @param args ���ռλ���Ŀɱ����
	 * @return ���¼��Ӧ�����һ��List
	 */
	List<T> queryForList(String sql,Object...args);
	
	/**
	 * ִ��һ�����Ի�ֵ�Ĳ�ѯ�����������ѯĳһ����¼��һ���ֶΣ����߲�ѯĳ��ͳ����Ϣ������Ҫ��ѯ��ֵ
	 * @param sql ��ִ�е�SQL���
	 * @param args ���ռλ���Ŀɱ����
	 * @return ִ��һ�����Ի�ֵ�Ĳ�ѯ�����������ѯһ����¼��һ���ֶΣ����߲�ѯĳ��ͳ����Ϣ������Ҫ��ѯ��ֵ
	 */
	<V> V getSingleVal(String sql,Object...args);
	
	/**
	 * ִ���������²���
	 * @param sql ��ִ�е�SQL���
	 * @param params ���ռλ���Ŀɱ����
	 */
	void batch(String sql,Object[]...params);
}
