/**
 * 
 */
package dmdd.dmddjava.dataaccess.hibernate.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.dataaccess.dataobject.Product;

/**
 * @author liuzhen
 * 
 */
public class DaoProduct extends Dao {

    /**
     * @param _session
     */
    public DaoProduct(Session _session) {
        super(_session);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

    public Product getProductTreeRoot() {
        Criteria crit = this.getSession().createCriteria(Product.class);
        crit.add(Restrictions.isNull("parentProduct.id"));
        Product treeRootProduct = (Product) crit.uniqueResult();
        return treeRootProduct;
    }

    public Product getProductById(Long _id) {
        Object obj = this.getSession().get(Product.class, _id);
        if (obj == null) {
            return null;
        }

        return (Product) obj;
    }

    /**
     * 查询 _id 的所有下级
     * 
     * @param _id
     * @param _blIncludeSelf
     *            是否包含 _id 本身
     * @return
     */
    public List<Product> getDescendentProducts(Long _id, boolean _blIncludeSelf) {
        if (_id == null) {
            return null;
        }

        List<Product> rstList = new ArrayList<Product>();
        if (ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_ORACLE)) {
            Criteria crit = this.getSession().createCriteria(Product.class);
            String sqlRestriction = null;
            if (_blIncludeSelf == true) {
                sqlRestriction = " (1=1) start with id = " + _id + " connect by prior id = parentproductid ";
            } else {
                sqlRestriction = " (1=1) start with parentproductid = " + _id + " connect by prior id = parentproductid ";
            }

            crit.add(Restrictions.sqlRestriction(sqlRestriction));

            rstList = crit.list();
        } else if (ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_SQLSERVER)
                || ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_DB2)) {
            if (_blIncludeSelf == true) {
                rstList = getProducts(_id);
            } else {
                Criteria crit = this.getSession().createCriteria(Product.class);
                String sqlRestriction = "parentproductid =" + _id;
                crit.add(Restrictions.sqlRestriction(sqlRestriction));
                List<Product> _SubList = crit.list();
                for (Product _product : _SubList) {
                    rstList.addAll(getProducts(_product.getId()));
                }
            }
        }
        return rstList;
    }

    public List<Product> getProducts(String _sqlRestriction) {
        Criteria crit = this.getSession().createCriteria(Product.class);
        if (_sqlRestriction != null && !(_sqlRestriction.equals(""))) {
            crit.add(Restrictions.sqlRestriction(_sqlRestriction));
        }
        List<Product> rstList = crit.list();
        return rstList;
    }

    public Product getDetailProductByCode(String _code) {
        Criteria crit = this.getSession().createCriteria(Product.class);
        crit.add(Restrictions.eq("isCatalog", BizConst.GLOBAL_YESNO_NO));
        crit.add(Restrictions.eq("code", _code));
        return (Product) crit.uniqueResult();
    }

    public Product getProductByCode(String _code) {
        Criteria crit = this.getSession().createCriteria(Product.class);
        // crit.add(Restrictions.eq( "name", _name) );
        crit.add(Restrictions.eq("code", _code));
        return (Product) crit.uniqueResult();
    }

    public List<Product> getProducts(Long _id) {
        List<Product> rstList = new ArrayList<Product>();
        if (_id != null) {
            Product product = getProductById(_id);
            if (product != null) {
                rstList.add(product);
            }
        }
        Criteria crit = this.getSession().createCriteria(Product.class);
        String _sqlRestriction = "PARENTPRODUCTID = " + _id;
        crit.add(Restrictions.sqlRestriction(_sqlRestriction));
        List<Product> _Sublist = crit.list();
        for (Product _product : _Sublist) {
            rstList.addAll(getProducts(_product.getId()));
        }
        return rstList;
    }

    public List<Product> getSubProducts(Product _product) {
        List<Product> list = new ArrayList<Product>();

        if (_product != null) {
            list.add(_product);
        }

        Criteria crit = this.getSession().createCriteria(Product.class);
        String _sqlRestriction = " PARENTPRODUCTID = " + _product.getId();
        crit.add(Restrictions.sqlRestriction(_sqlRestriction));
        List<Product> _subList = crit.list();
        for (Product product : _subList) {
            list.addAll(getSubProducts(product));
        }

        return list;
    }
}
