/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables;


import cn.mooc.jooq.demo.generated.AppcenterMallData;
import cn.mooc.jooq.demo.generated.Keys;
import cn.mooc.jooq.demo.generated.tables.records.TMallOrderrefundsbatchnoRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


/**
 * t_OrderRefundsBatchNo
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TMallOrderrefundsbatchno extends TableImpl<TMallOrderrefundsbatchnoRecord> {

    private static final long serialVersionUID = 1301495720;

    /**
     * The reference instance of <code>appcenter_mall_data.t_mall_orderrefundsbatchno</code>
     */
    public static final TMallOrderrefundsbatchno T_MALL_ORDERREFUNDSBATCHNO = new TMallOrderrefundsbatchno();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TMallOrderrefundsbatchnoRecord> getRecordType() {
        return TMallOrderrefundsbatchnoRecord.class;
    }

    /**
     * The column <code>appcenter_mall_data.t_mall_orderrefundsbatchno.Id</code>. 编号
     */
    public final TableField<TMallOrderrefundsbatchnoRecord, Long> ID = createField("Id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "编号");

    /**
     * The column <code>appcenter_mall_data.t_mall_orderrefundsbatchno.DateNum</code>. 日期数字
     */
    public final TableField<TMallOrderrefundsbatchnoRecord, Integer> DATENUM = createField("DateNum", org.jooq.impl.SQLDataType.INTEGER, this, "日期数字");

    /**
     * The column <code>appcenter_mall_data.t_mall_orderrefundsbatchno.BatchNo</code>. BatchNo
     */
    public final TableField<TMallOrderrefundsbatchnoRecord, Integer> BATCHNO = createField("BatchNo", org.jooq.impl.SQLDataType.INTEGER, this, "BatchNo");

    /**
     * The column <code>appcenter_mall_data.t_mall_orderrefundsbatchno.refundId</code>. 退单编号
     */
    public final TableField<TMallOrderrefundsbatchnoRecord, Long> REFUNDID = createField("refundId", org.jooq.impl.SQLDataType.BIGINT, this, "退单编号");

    /**
     * Create a <code>appcenter_mall_data.t_mall_orderrefundsbatchno</code> table reference
     */
    public TMallOrderrefundsbatchno() {
        this("t_mall_orderrefundsbatchno", null);
    }

    /**
     * Create an aliased <code>appcenter_mall_data.t_mall_orderrefundsbatchno</code> table reference
     */
    public TMallOrderrefundsbatchno(String alias) {
        this(alias, T_MALL_ORDERREFUNDSBATCHNO);
    }

    private TMallOrderrefundsbatchno(String alias, Table<TMallOrderrefundsbatchnoRecord> aliased) {
        this(alias, aliased, null);
    }

    private TMallOrderrefundsbatchno(String alias, Table<TMallOrderrefundsbatchnoRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "t_OrderRefundsBatchNo");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return AppcenterMallData.APPCENTER_MALL_DATA;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<TMallOrderrefundsbatchnoRecord, Long> getIdentity() {
        return Keys.IDENTITY_T_MALL_ORDERREFUNDSBATCHNO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TMallOrderrefundsbatchnoRecord> getPrimaryKey() {
        return Keys.KEY_T_MALL_ORDERREFUNDSBATCHNO_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TMallOrderrefundsbatchnoRecord>> getKeys() {
        return Arrays.<UniqueKey<TMallOrderrefundsbatchnoRecord>>asList(Keys.KEY_T_MALL_ORDERREFUNDSBATCHNO_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallOrderrefundsbatchno as(String alias) {
        return new TMallOrderrefundsbatchno(alias, this);
    }

    /**
     * Rename this table
     */
    public TMallOrderrefundsbatchno rename(String name) {
        return new TMallOrderrefundsbatchno(name, null);
    }
}
