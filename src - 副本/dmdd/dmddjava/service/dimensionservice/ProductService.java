package dmdd.dmddjava.service.dimensionservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.cool.common.constant.DMOConst;
import com.cool.common.logging.CoolLogger;
import com.cool.common.util.HashVoUtil;
import com.cool.common.vo.HashVO;
import com.cool.dbaccess.CommDMO;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.common.constant.ExceptionConst;
import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.dataaccess.aidobject.ABImProduct;
import dmdd.dmddjava.dataaccess.aidobject.ABImProductCharacter;
import dmdd.dmddjava.dataaccess.aidobject.ABImProductProCharacter;
import dmdd.dmddjava.dataaccess.bdconvertor.ProductBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.ProductCharacterBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.ProductCharacterLayerBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.ProductLayerBDConvertor;
import dmdd.dmddjava.dataaccess.bizobject.BProduct;
import dmdd.dmddjava.dataaccess.bizobject.BProductCharacter;
import dmdd.dmddjava.dataaccess.bizobject.BProductCharacterLayer;
import dmdd.dmddjava.dataaccess.bizobject.BProductLayer;
import dmdd.dmddjava.dataaccess.bizobject.BSuitSkuRel;
import dmdd.dmddjava.dataaccess.dataobject.Product;
import dmdd.dmddjava.dataaccess.dataobject.ProductCharacter;
import dmdd.dmddjava.dataaccess.dataobject.ProductCharacterLayer;
import dmdd.dmddjava.dataaccess.dataobject.ProductLayer;
import dmdd.dmddjava.dataaccess.dataobject.ProductProCharacter;
import dmdd.dmddjava.dataaccess.dataobject.Unit;
import dmdd.dmddjava.dataaccess.dataobject.UnitGroup;
import dmdd.dmddjava.dataaccess.hibernate.HibernateSessionFactory;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoProduct;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoProductCharacter;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoProductCharacterLayer;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoProductLayer;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoProductProCharacter;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoUnitGroup;
import dmdd.dmddjava.dm.ProductSuitDM;

/**
 * @author liuzhen
 * 
 */
public class ProductService {
    private Logger logger = CoolLogger.getLogger(this.getClass());

    private CommDMO dmo = new CommDMO();
    
    private ProductSuitDM suitDm = new ProductSuitDM();

    // ProductCharacterLayer begin
    public BProductCharacterLayer newProductCharacterLayer(BProductCharacterLayer _bProductCharacterLayer4new) throws Exception {
        // 检查服务器状态是否可以提供服务 begin
        ServerEnvironment.getInstance().checkSystemStatus();
        // 检查服务器状态是否可以提供服务 end

        BProductCharacterLayer bProductCharacterLayer_rst = null;
        ProductCharacterLayerBDConvertor productCharacterLayerBDConvertor = new ProductCharacterLayerBDConvertor();
        ProductCharacterLayer productCharacterLayer_new = (ProductCharacterLayer) productCharacterLayerBDConvertor.btod(_bProductCharacterLayer4new);

        Session session = HibernateSessionFactory.getSession();
        Transaction trsa = null;
        try {
            trsa = session.beginTransaction();
            DaoProductCharacterLayer daoProductCharacterLayer = new DaoProductCharacterLayer(session);
            daoProductCharacterLayer.save(productCharacterLayer_new);
            trsa.commit();

            bProductCharacterLayer_rst = (BProductCharacterLayer) productCharacterLayerBDConvertor.dtob(productCharacterLayer_new);
        } catch (Exception ex) {
            if (trsa != null) {
                trsa.rollback();
            }
            ex.printStackTrace();
            throw ex;
        } finally {
            session.close();
        }

        return bProductCharacterLayer_rst;

    }

    public BProductCharacterLayer updProductCharacterLayer(BProductCharacterLayer _bProductCharacterLayer4upd) throws Exception {
        // 检查服务器状态是否可以提供服务 begin
        ServerEnvironment.getInstance().checkSystemStatus();
        // 检查服务器状态是否可以提供服务 end

        BProductCharacterLayer bProductCharacterLayer_rst = null;
        ProductCharacterLayerBDConvertor productCharacterLayerBDConvertor = new ProductCharacterLayerBDConvertor();
        ProductCharacterLayer productCharacterLayer_upd = (ProductCharacterLayer) productCharacterLayerBDConvertor.btod(_bProductCharacterLayer4upd);

        Session session = HibernateSessionFactory.getSession();
        Transaction trsa = null;
        try {
            trsa = session.beginTransaction();
            DaoProductCharacterLayer daoProductCharacterLayer = new DaoProductCharacterLayer(session);
            daoProductCharacterLayer.update(productCharacterLayer_upd);
            trsa.commit();

            bProductCharacterLayer_rst = (BProductCharacterLayer) productCharacterLayerBDConvertor.dtob(productCharacterLayer_upd);
        } catch (Exception ex) {
            if (trsa != null) {
                trsa.rollback();
            }
            ex.printStackTrace();
            throw ex;
        } finally {
            session.close();
        }

        BProductCharacter bProductCharacter_treeRoot = this.getProductCharacterTreeRoot(); // 返回整个树形
        ServerEnvironment.getInstance().setBProductCharacterTreeRoot(bProductCharacter_treeRoot);

        return bProductCharacterLayer_rst;

    }

    public boolean delProductCharacterLayer(BProductCharacterLayer _bProductCharacterLayer4del) throws Exception {
        // 检查服务器状态是否可以提供服务 begin
        ServerEnvironment.getInstance().checkSystemStatus();
        // 检查服务器状态是否可以提供服务 end

        ProductCharacterLayerBDConvertor productCharacterLayerBDConvertor = new ProductCharacterLayerBDConvertor();
        ProductCharacterLayer productCharacterLayer_del = (ProductCharacterLayer) productCharacterLayerBDConvertor.btod(_bProductCharacterLayer4del);

        Session session = HibernateSessionFactory.getSession();
        Transaction trsa = null;
        try {
            trsa = session.beginTransaction();
            DaoProductCharacterLayer daoProductCharacterLayer = new DaoProductCharacterLayer(session);
            daoProductCharacterLayer.delete(productCharacterLayer_del);

            trsa.commit();
        } catch (Exception ex) {
            if (trsa != null) {
                trsa.rollback();
            }
            ex.printStackTrace();
            throw ex;
        } finally {
            session.close();
        }

        return true;
    }

    public List<BProductCharacterLayer> getAllProductCharacterLayers() throws Exception {
        // 检查服务器状态是否可以提供服务 begin
        // 用户登录后会与系统状态一起获取这个数据，不做检查
        // ServerEnvironment.getInstance().checkSystemStatus();
        // 检查服务器状态是否可以提供服务 end

        List<BProductCharacterLayer> rstList = new ArrayList<BProductCharacterLayer>();

        Session session = HibernateSessionFactory.getSession();
        Transaction trsa = null;
        try {
            trsa = session.beginTransaction();
            DaoProductCharacterLayer daoProductCharacterLayer = new DaoProductCharacterLayer(session);
            List<ProductCharacterLayer> listProductCharacterLayer_inDB = daoProductCharacterLayer.getAllProductCharacterLayers();

            if (listProductCharacterLayer_inDB != null && !(listProductCharacterLayer_inDB.isEmpty())) {
                ProductCharacterLayerBDConvertor productCharacterLayerBDConvertor = new ProductCharacterLayerBDConvertor();
                for (int i = 0; i < listProductCharacterLayer_inDB.size(); i++) {
                    rstList.add((BProductCharacterLayer) productCharacterLayerBDConvertor.dtob(listProductCharacterLayer_inDB.get(i)));
                }
            }

            trsa.commit();
        } catch (Exception ex) {
            if (trsa != null) {
                trsa.rollback();
            }
            ex.printStackTrace();
            throw ex;
        } finally {
            session.close();
        }

        return rstList;

    }

    // ProductCharacterLayer end

    // ProductLayer begin
    public BProductLayer newProductLayer(BProductLayer _bProductLayer4new) throws Exception {
        // 检查服务器状态是否可以提供服务 begin
        ServerEnvironment.getInstance().checkSystemStatus();
        // 检查服务器状态是否可以提供服务 end

        BProductLayer bProductLayer_rst = null;
        ProductLayerBDConvertor productLayerBDConvertor = new ProductLayerBDConvertor();
        ProductLayer productLayer_new = (ProductLayer) productLayerBDConvertor.btod(_bProductLayer4new);

        Session session = HibernateSessionFactory.getSession();
        Transaction trsa = null;
        try {
            trsa = session.beginTransaction();
            DaoProductLayer daoProductLayer = new DaoProductLayer(session);
            daoProductLayer.save(productLayer_new);
            trsa.commit();

            bProductLayer_rst = (BProductLayer) productLayerBDConvertor.dtob(productLayer_new);
        } catch (Exception ex) {
            if (trsa != null) {
                trsa.rollback();
            }
            ex.printStackTrace();
            throw ex;
        } finally {
            session.close();
        }

        return bProductLayer_rst;

    }

    public BProductLayer updProductLayer(BProductLayer _bProductLayer4upd) throws Exception {
        // 检查服务器状态是否可以提供服务 begin
        ServerEnvironment.getInstance().checkSystemStatus();
        // 检查服务器状态是否可以提供服务 end

        BProductLayer bProductLayer_rst = null;
        ProductLayerBDConvertor productLayerBDConvertor = new ProductLayerBDConvertor();
        ProductLayer productLayer_upd = (ProductLayer) productLayerBDConvertor.btod(_bProductLayer4upd);

        Session session = HibernateSessionFactory.getSession();
        Transaction trsa = null;
        try {
            trsa = session.beginTransaction();
            DaoProductLayer daoProductLayer = new DaoProductLayer(session);
            daoProductLayer.update(productLayer_upd);
            trsa.commit();

            bProductLayer_rst = (BProductLayer) productLayerBDConvertor.dtob(productLayer_upd);
        } catch (Exception ex) {
            if (trsa != null) {
                trsa.rollback();
            }
            ex.printStackTrace();
            throw ex;
        } finally {
            session.close();
        }

        BProduct bProduct_rst = this.getProductTreeRoot(); // 返回整个树形
        // 主数据常驻内存，2011.01.15 by liuzhen begin
        ServerEnvironment.getInstance().setBProductTreeRoot(bProduct_rst);
        // 主数据常驻内存，2011.01.15 by liuzhen end
        return bProductLayer_rst;

    }

    public boolean delProductLayer(BProductLayer _bProductLayer4del) throws Exception {
        // 检查服务器状态是否可以提供服务 begin
        ServerEnvironment.getInstance().checkSystemStatus();
        // 检查服务器状态是否可以提供服务 end

        ProductLayerBDConvertor productLayerBDConvertor = new ProductLayerBDConvertor();
        ProductLayer productLayer_del = (ProductLayer) productLayerBDConvertor.btod(_bProductLayer4del);

        Session session = HibernateSessionFactory.getSession();
        Transaction trsa = null;
        try {
            trsa = session.beginTransaction();
            DaoProductLayer daoProductLayer = new DaoProductLayer(session);
            daoProductLayer.delete(productLayer_del);

            trsa.commit();
        } catch (Exception ex) {
            if (trsa != null) {
                trsa.rollback();
            }
            ex.printStackTrace();
            throw ex;
        } finally {
            session.close();
        }

        return true;
    }

    public List<BProductLayer> getAllProductLayers() throws Exception {
        // 检查服务器状态是否可以提供服务 begin
        // 用户登录后会与系统状态一起获取这个数据，不做检查
        // ServerEnvironment.getInstance().checkSystemStatus();
        // 检查服务器状态是否可以提供服务 end

        List<BProductLayer> rstList = new ArrayList<BProductLayer>();

        Session session = HibernateSessionFactory.getSession();
        Transaction trsa = null;
        try {
            trsa = session.beginTransaction();
            DaoProductLayer daoProductLayer = new DaoProductLayer(session);
            List<ProductLayer> listProductLayer_inDB = daoProductLayer.getAllProductLayers();

            if (listProductLayer_inDB != null && !(listProductLayer_inDB.isEmpty())) {
                ProductLayerBDConvertor productLayerBDConvertor = new ProductLayerBDConvertor();
                for (int i = 0; i < listProductLayer_inDB.size(); i++) {
                    rstList.add((BProductLayer) productLayerBDConvertor.dtob(listProductLayer_inDB.get(i)));
                }
            }

            trsa.commit();
        } catch (Exception ex) {
            if (trsa != null) {
                trsa.rollback();
            }
            ex.printStackTrace();
            throw ex;
        } finally {
            session.close();
        }

        return rstList;

    }

    // ProductLayer end

    // ProductCharacter begin

    public BProductCharacter getProductCharacterTreeRoot4UI() throws Exception {
        return ServerEnvironment.getInstance().getBProductCharacterTreeRoot();
    }

    /**
     * 新建 返回节点本身
     */
    public BProductCharacter newProductCharacter(BProductCharacter _bProductCharacter4new) throws Exception {
        // 检查服务器状态是否可以提供服务 begin
        ServerEnvironment.getInstance().checkSystemStatus();
        // 检查服务器状态是否可以提供服务 end

        BProductCharacter bProductCharacter_rst = null;

        ProductCharacterBDConvertor productCharacterBDConvertor = new ProductCharacterBDConvertor();
        ProductCharacter productCharacter_new = (ProductCharacter) productCharacterBDConvertor.btod(_bProductCharacter4new);
        if (_bProductCharacter4new.getParentProductCharacter() != null) {
            ProductCharacter parentProductCharacter = (ProductCharacter) productCharacterBDConvertor.btod(_bProductCharacter4new.getParentProductCharacter());
            productCharacter_new.setParentProductCharacter(parentProductCharacter);
        } else {
            productCharacter_new.setParentProductCharacter(null);
        }

        Session session = HibernateSessionFactory.getSession();
        Transaction trsa = null;
        try {
            trsa = session.beginTransaction();
            DaoProductCharacter daoProductCharacter = new DaoProductCharacter(session);
            // 检查父节点来测试保证父节点最新 begin
            if (_bProductCharacter4new.getParentProductCharacter() != null) {
                ProductCharacter productCharacter_parent_inDB = daoProductCharacter.getProductCharacterById(_bProductCharacter4new.getParentProductCharacter()
                        .getId());
                if (productCharacter_parent_inDB != null
                        && productCharacter_parent_inDB.getVersion().longValue() != _bProductCharacter4new.getParentProductCharacter().getVersion().longValue()) {
                    Throwable cause = new Throwable(ExceptionConst.EXCEPTION_CAUSECODE_PARENTNODE_HAS_BEEN_MODIFIED);
                    Exception ex = new Exception(cause);
                    throw ex;
                }
            }
            // 检查父节点来测试保证父节点最新 end
            productCharacter_new = (ProductCharacter) daoProductCharacter.save(productCharacter_new);
            if (_bProductCharacter4new.getParentProductCharacter() == null)
                productCharacter_new.setPathCode("" + productCharacter_new.getId());
            else
                productCharacter_new.setPathCode(_bProductCharacter4new.getParentProductCharacter().getPathCode() + "-" + productCharacter_new.getId());
            daoProductCharacter.save(productCharacter_new);
            trsa.commit();
        } catch (Exception ex) {
            if (trsa != null) {
                trsa.rollback();
            }
            ex.printStackTrace();
            throw ex;
        } finally {
            session.close();
        }

        // 主数据常驻内存，2011.01.15 by liuzhen begin
        /*
         * bProductCharacter_rst = this.getProductCharacterTree(
         * productCharacter_new.getId() );
         */
        BProductCharacter bProductCharacter_treeRoot = this.getProductCharacterTreeRoot(); // 返回整个树形
        ServerEnvironment.getInstance().setBProductCharacterTreeRoot(bProductCharacter_treeRoot);
        bProductCharacter_rst = ServerEnvironment.getInstance().getBProductCharacter(productCharacter_new.getId());
        // 主数据常驻内存，2011.01.15 by liuzhen end
        return bProductCharacter_rst;

    }

    /**
     * 返回整个树
     * 
     * @param _bProductCharacter4upd
     * @return
     * @throws Exception
     */
    public BProductCharacter updProductCharacter(BProductCharacter _bProductCharacter4upd) throws Exception {
        // 检查服务器状态是否可以提供服务 begin
        ServerEnvironment.getInstance().checkSystemStatus();
        // 检查服务器状态是否可以提供服务 end

        if (_bProductCharacter4upd == null) {
            Exception ex = new Exception("The object to update is a null object");
            throw ex;
        }

        int diffLayerChange = 0;
        List<ProductCharacter> toUpdateProductCharacterList_descendent = new ArrayList<ProductCharacter>();
        HashMap<Long, Integer> hmap_layerValue_old = new HashMap<Long, Integer>();

        Session querySession = HibernateSessionFactory.getSession();
        Transaction queryTrsa = null;
        try {
            queryTrsa = querySession.beginTransaction();
            DaoProductCharacter daoProductCharacter_query = new DaoProductCharacter(querySession);
            ProductCharacter productCharacter_inDB = daoProductCharacter_query.getProductCharacterById(_bProductCharacter4upd.getId());

            if (productCharacter_inDB == null) {
                Throwable cause = new Throwable(ExceptionConst.EXCEPTION_CAUSECODE_OBJECT_NOT_IN_DATABASE);
                Exception ex = new Exception(cause);
                throw ex;
            }

            boolean blTypeChange = false;
            if (productCharacter_inDB.getType() != null) {
                if (!(productCharacter_inDB.getType().equals(_bProductCharacter4upd.getType()))) {
                    blTypeChange = true;
                }
            }

            diffLayerChange = _bProductCharacter4upd.getProductCharacterLayer().getValue() - productCharacter_inDB.getProductCharacterLayer().getValue();

            if (_bProductCharacter4upd.getIsCatalog() == BizConst.GLOBAL_YESNO_YES && (blTypeChange == true || diffLayerChange != 0)) {
                toUpdateProductCharacterList_descendent = daoProductCharacter_query.getDescendentProductCharacters(productCharacter_inDB.getId(), false);
                if (toUpdateProductCharacterList_descendent != null && !(toUpdateProductCharacterList_descendent.isEmpty())) {
                    for (int i = 0; i < toUpdateProductCharacterList_descendent.size(); i = i + 1) {
                        ProductCharacter productCharacter_descendent = toUpdateProductCharacterList_descendent.get(i);
                        hmap_layerValue_old.put(productCharacter_descendent.getId(), productCharacter_descendent.getProductCharacterLayer().getValue());
                    }
                }
                // 注意，这里只是读出来，还不能改。要放到下面的事务中一起修改
            }

            queryTrsa.commit();

        } catch (Exception ex) {
            if (queryTrsa != null) {
                queryTrsa.rollback();
            }
            ex.printStackTrace();
            throw ex;
        } finally {
            querySession.close();
        }

        BProductCharacter bProductCharacter_rst = null;

        ProductCharacterBDConvertor productCharacterBDConvertor = new ProductCharacterBDConvertor();
        ProductCharacter productCharacter_upd = (ProductCharacter) productCharacterBDConvertor.btod(_bProductCharacter4upd);
        if (_bProductCharacter4upd.getParentProductCharacter() != null) {
            ProductCharacter upddobjParentProductCharacter = (ProductCharacter) productCharacterBDConvertor.btod(_bProductCharacter4upd
                    .getParentProductCharacter());
            productCharacter_upd.setParentProductCharacter(upddobjParentProductCharacter);
            productCharacter_upd.setPathCode(_bProductCharacter4upd.getParentProductCharacter().getPathCode() + "-" + productCharacter_upd.getId());
        } else {
            productCharacter_upd.setParentProductCharacter(null);
            productCharacter_upd.setPathCode("" + productCharacter_upd.getId());
        }

        Session session = HibernateSessionFactory.getSession();
        Transaction trsa = null;
        try {
            trsa = session.beginTransaction();
            DaoProductCharacter daoProductCharacter = new DaoProductCharacter(session);
            daoProductCharacter.update(productCharacter_upd);

            // 更新下级特征(组) begin
            if (toUpdateProductCharacterList_descendent != null && !(toUpdateProductCharacterList_descendent.isEmpty())) {
                DaoProductCharacterLayer daoProductCharacterLayer = new DaoProductCharacterLayer(session);
                for (int i = 0; i < toUpdateProductCharacterList_descendent.size(); i++) {
                    ProductCharacter productCharacter_upd_descendent = toUpdateProductCharacterList_descendent.get(i);

                    productCharacter_upd_descendent.setType(_bProductCharacter4upd.getType());

                    if (diffLayerChange != 0) {
                        int newLayerValue = hmap_layerValue_old.get(productCharacter_upd_descendent.getId()) + diffLayerChange;
                        ProductCharacterLayer productCharacterLayer = daoProductCharacterLayer.getProductCharacterLayerByValue(newLayerValue);
                        if (productCharacterLayer == null) {
                            Throwable cause = new Throwable(ExceptionConst.EXCEPTION_CAUSECODE_PRODUCTCHARACTERLAYER_MAXLAYER);
                            Exception ex = new Exception(cause);
                            throw ex;

                        } else {
                            productCharacter_upd_descendent.setProductCharacterLayer(productCharacterLayer);
                        }
                    }

                    daoProductCharacter.update(productCharacter_upd_descendent);
                }
            }

            trsa.commit();
        } catch (Exception ex) {
            if (trsa != null) {
                trsa.rollback();
            }
            ex.printStackTrace();
            throw ex;
        } finally {
            session.close();
        }

        bProductCharacter_rst = this.getProductCharacterTreeRoot(); // 返回整个树形
        // 主数据常驻内存，2011.01.15 by liuzhen begin
        ServerEnvironment.getInstance().setBProductCharacterTreeRoot(bProductCharacter_rst);
        // 主数据常驻内存，2011.01.15 by liuzhen end

        BProduct bProduct_rst = this.getProductTreeRoot(); // 返回整个树形
        // 主数据常驻内存，2011.01.15 by liuzhen begin
        ServerEnvironment.getInstance().setBProductTreeRoot(bProduct_rst);
        // 主数据常驻内存，2011.01.15 by liuzhen end

        return bProductCharacter_rst;
    }

    public boolean delProductCharacter(BProductCharacter _bProductCharacter4del) throws Exception {
        // 检查服务器状态是否可以提供服务 begin
        ServerEnvironment.getInstance().checkSystemStatus();
        // 检查服务器状态是否可以提供服务 end

        if (_bProductCharacter4del == null) {
            Exception ex = new Exception("The object to delete is null! Do nothing!");
            throw ex;
        }

        ProductCharacterBDConvertor productCharacterBDConvertor = new ProductCharacterBDConvertor();
        ProductCharacter productCharacter_del = (ProductCharacter) productCharacterBDConvertor.btod(_bProductCharacter4del);

        Session session = HibernateSessionFactory.getSession();
        Transaction trsa = null;
        try {
            trsa = session.beginTransaction();
            // 判断产品特征是否已经关联了产品
            DaoProductProCharacter daoProductProCharacter = new DaoProductProCharacter(session);
            DaoProductCharacter daoProductCharacter = new DaoProductCharacter(session);
            List<ProductCharacter> listProductCharacter = daoProductCharacter.getSubProductCharacters(productCharacter_del);
            for (ProductCharacter productCharacter : listProductCharacter) {
                List<ProductProCharacter> list = daoProductProCharacter.getProductProCharactersByProCharacter(productCharacter.getId());
                if (list != null && !list.isEmpty())// 已经有关联
                {
                    Throwable cause = new Throwable(ExceptionConst.EXCEPTION_CAUSECODE_PRODUCTCHARACTER_DELETEERROR);
                    Exception ex = new Exception(cause);
                    throw ex;
                }
            }

            // 数据库设置成绩级联删除由于sqlserver没法做到级联的删除，因此通过代码来解决
            // daoProductCharacter.delete( productCharacter_del );
            if (ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_ORACLE)) {
                daoProductCharacter.delete(productCharacter_del);
            } else if (ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_SQLSERVER)
                    || ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_DB2)) {
                for (ProductCharacter _productCharacter : listProductCharacter) {
                    daoProductCharacter.delete(_productCharacter);
                }
            }
            trsa.commit();
        } catch (Exception ex) {
            if (trsa != null) {
                trsa.rollback();
            }
            ex.printStackTrace();
            throw ex;
        } finally {
            session.close();
        }

        // 主数据常驻内存，2011.01.15 by liuzhen begin
        BProductCharacter bProductCharacter_treeRoot = this.getProductCharacterTreeRoot(); // 返回整个树形
        ServerEnvironment.getInstance().setBProductCharacterTreeRoot(bProductCharacter_treeRoot);
        // 主数据常驻内存，2011.01.15 by liuzhen end

        BProduct bProduct_rst = this.getProductTreeRoot(); // 返回整个树形
        // 主数据常驻内存，2011.01.15 by liuzhen begin
        ServerEnvironment.getInstance().setBProductTreeRoot(bProduct_rst);
        // 主数据常驻内存，2011.01.15 by liuzhen end

        return true;
    }

    /**
     * 从数据库读取 ProductCharacter 的唯一入口， 其他的都是用 内存常驻变量
     * 
     * @return
     * @throws Exception
     */
    public BProductCharacter getProductCharacterTreeRoot() throws Exception {
        BProductCharacter bProductCharacter_treeRoot = null;

        Session session = HibernateSessionFactory.getSession();
        Transaction trsa = null;
        try {
            trsa = session.beginTransaction();
            DaoProductCharacter daoProductCharacter = new DaoProductCharacter(session);
            ProductCharacter rootProductCharacter_inDB = null;
            rootProductCharacter_inDB = daoProductCharacter.getProductCharacterTreeRoot();

            if (rootProductCharacter_inDB != null) {
                bProductCharacter_treeRoot = this.getBProductCharacterTreeByDProductCharacter(rootProductCharacter_inDB);
                bProductCharacter_treeRoot.setParentProductCharacter(null);
            }

            trsa.commit();
        } catch (Exception ex) {
            if (trsa != null) {
                trsa.rollback();
            }
            ex.printStackTrace();
            throw ex;
        } finally {
            session.close();
        }

        return bProductCharacter_treeRoot;

    }

    /**
     * 产品特征导入
     * 
     * @param _list4ABImProductCharacter
     * @return
     * @throws Exception
     */
    public List<ABImProductCharacter> importProductCharacter(List<ABImProductCharacter> _list4ABImProductCharacter) throws Exception {
        // 检查服务器状态是否可以提供服务 begin
        ServerEnvironment.getInstance().checkSystemStatus();
        // 检查服务器状态是否可以提供服务 end

        List<ABImProductCharacter> rstList = new ArrayList<ABImProductCharacter>();

        if (_list4ABImProductCharacter == null || _list4ABImProductCharacter.isEmpty()) {
            throw new Exception("Paramete is not correct");
        }

        String sqlRestriction = null;
        ABImProductCharacter abImProductCharacter = null;
        String importResult = null;

        ProductCharacter parentProductCharacter = null;
        ProductCharacterLayer productCharacterLayer = null;

        String code = null;
        String name = null;
        String type = null;
        int isCatalog;
        String description = null;
        String comments = null;

        for (int i = 0; i < _list4ABImProductCharacter.size(); i++) {
            // 逐行处理
            abImProductCharacter = _list4ABImProductCharacter.get(i);

            Session session = HibernateSessionFactory.getSession();
            Transaction trsa = null;
            try {
                trsa = session.beginTransaction();

                DaoProductCharacter daoProductCharacter = new DaoProductCharacter(session);
                DaoProductCharacterLayer daoProductCharacterLayer = new DaoProductCharacterLayer(session);

                // 上级产品特征
                parentProductCharacter = null;
                if (abImProductCharacter.getParentCode() == null || abImProductCharacter.getParentCode().trim().equals("")) {
                    importResult = "Fail: parentCode is null";
                    abImProductCharacter.setImportResult(importResult);

                    rstList.add(abImProductCharacter);
                    continue;
                }
                sqlRestriction = "code = '" + abImProductCharacter.getParentCode().trim() + "'";
                List<ProductCharacter> listProductCharacter_inDB = daoProductCharacter.getProductCharacters(sqlRestriction);
                if (listProductCharacter_inDB == null || listProductCharacter_inDB.isEmpty()) {
                    importResult = "Fail: can not find parent productCharacter";
                    abImProductCharacter.setImportResult(importResult);

                    rstList.add(abImProductCharacter);
                    continue;
                }
                if (listProductCharacter_inDB.size() > 1) {
                    importResult = "Fail: find multiple parent productCharacter";
                    abImProductCharacter.setImportResult(importResult);

                    rstList.add(abImProductCharacter);
                    continue;
                }
                parentProductCharacter = listProductCharacter_inDB.get(0);
                if (parentProductCharacter.getIsCatalog() == BizConst.GLOBAL_YESNO_NO) {
                    importResult = "Fail: parent productCharacter is not a catalog";
                    abImProductCharacter.setImportResult(importResult);

                    rstList.add(abImProductCharacter);
                    continue;
                }

                // 层次
                productCharacterLayer = null;
                if (abImProductCharacter.getLayerValue() == null || abImProductCharacter.getLayerValue().trim().equals("")) {
                    importResult = "Fail: layerValue is null";
                    abImProductCharacter.setImportResult(importResult);

                    rstList.add(abImProductCharacter);
                    continue;
                }

                int layerValue = 0;
                try {
                    layerValue = Integer.parseInt(abImProductCharacter.getLayerValue().trim());
                } catch (Exception ex) {
                    importResult = "Fail: layerValue is not valid integer";
                    abImProductCharacter.setImportResult(importResult);

                    rstList.add(abImProductCharacter);
                    continue;
                }

                productCharacterLayer = daoProductCharacterLayer.getProductCharacterLayerByValue(layerValue);
                if (productCharacterLayer == null) {
                    importResult = "Fail: can not find productCharacterLayer";
                    abImProductCharacter.setImportResult(importResult);

                    rstList.add(abImProductCharacter);
                    continue;
                }

                if (productCharacterLayer.getValue() != parentProductCharacter.getProductCharacterLayer().getValue() + 1) {
                    importResult = "Fail: productLayer value != parent  productLayer value + 1";
                    abImProductCharacter.setImportResult(importResult);

                    rstList.add(abImProductCharacter);
                    continue;
                }

                // 编码
                code = null;

                if (abImProductCharacter.getCode() == null || abImProductCharacter.getCode().trim().equals("")) {
                    importResult = "Fail: code is null ";
                    abImProductCharacter.setImportResult(importResult);

                    rstList.add(abImProductCharacter);
                    continue;
                }
                code = abImProductCharacter.getCode().trim();

                // 名称
                name = null;

                if (abImProductCharacter.getName() == null || abImProductCharacter.getName().trim().equals("")) {
                    importResult = "Fail: name is null ";
                    abImProductCharacter.setImportResult(importResult);

                    rstList.add(abImProductCharacter);
                    continue;
                }
                name = abImProductCharacter.getName().trim();

                // 类型
                type = null;

                if (abImProductCharacter.getType() == null || abImProductCharacter.getType().trim().equals("")) {
                    importResult = "Fail: type is null ";
                    abImProductCharacter.setImportResult(importResult);

                    rstList.add(abImProductCharacter);
                    continue;
                }
                type = abImProductCharacter.getType().trim();
                if (productCharacterLayer.getValue() == 1) {
                    // 检查 TYPE 是否与已有的TYPE重复
                    List<String> listType_inDB = daoProductCharacter.getAllProductCharacterTypes();
                    if (listType_inDB != null && !(listType_inDB.isEmpty())) {
                        int t = -1;
                        for (t = 0; t < listType_inDB.size(); t++) {
                            String tmpType = listType_inDB.get(t);

                            if (tmpType.equals(type)) {
                                break;
                            }
                        }
                        if (t < listType_inDB.size()) {
                            importResult = "Fail: type is same with other type in layer 1 ";
                            abImProductCharacter.setImportResult(importResult);

                            rstList.add(abImProductCharacter);
                            continue;
                        }
                    }
                } else {
                    // 检查与 parent的type是否一致
                    if (!(parentProductCharacter.getType().equals(type))) {
                        importResult = "Fail: type is not same with parent ";
                        abImProductCharacter.setImportResult(importResult);

                        rstList.add(abImProductCharacter);
                        continue;
                    }
                }

                // 是否目录
                isCatalog = 0;

                if (abImProductCharacter.getIsCatalog() == null || abImProductCharacter.getIsCatalog().trim().equals("")) {
                    importResult = "Fail: isCatalog is null ";
                    abImProductCharacter.setImportResult(importResult);

                    rstList.add(abImProductCharacter);
                    continue;
                }
                if (abImProductCharacter.getIsCatalog().trim().equals("0")) {
                    isCatalog = 0;
                } else if (abImProductCharacter.getIsCatalog().trim().equals("1")) {
                    isCatalog = 1;
                } else {
                    importResult = "Fail: isCatalog is not 0 or 1 ";
                    abImProductCharacter.setImportResult(importResult);

                    rstList.add(abImProductCharacter);
                    continue;
                }

                // description
                description = null;
                if (abImProductCharacter.getDescription() != null) {
                    description = abImProductCharacter.getDescription().trim();
                }

                // comments
                comments = null;
                if (abImProductCharacter.getComments() != null) {
                    comments = abImProductCharacter.getComments().trim();
                }

                // 建立新 ProductCharacter begin
                ProductCharacter newProductCharacter = new ProductCharacter();

                newProductCharacter.setParentProductCharacter(parentProductCharacter);
                newProductCharacter.setProductCharacterLayer(productCharacterLayer);
                newProductCharacter.setCode(code);
                newProductCharacter.setName(name);
                newProductCharacter.setType(type);
                newProductCharacter.setIsCatalog(isCatalog);
                newProductCharacter.setDescription(description);
                newProductCharacter.setComments(comments);

                daoProductCharacter.save(newProductCharacter);
                // 建立新 ProductCharacter end

                trsa.commit();

                // 导入成功 begin
                importResult = BizConst.IMPORT_RESULT_SUCCESS;
                abImProductCharacter.setImportResult(importResult);

                rstList.add(abImProductCharacter);
                // 导入成功 end

            } catch (Exception ex) {
                if (trsa != null) {
                    trsa.rollback();
                }
                ex.printStackTrace();

                if (ex.getCause() != null) {
                    importResult = "Fail: " + ex.getCause().getMessage();
                } else {
                    importResult = "Fail: " + ex.getMessage();
                }

                abImProductCharacter.setImportResult(importResult);

                rstList.add(abImProductCharacter);

            } finally {
                session.close();
            }
        }

        // 主数据常驻内存，2011.02.25 by liuzhen begin
        ServerEnvironment.getInstance().setBProductCharacterTreeRoot(this.getProductCharacterTreeRoot());
        
        //刷新产品缓存
        refreshCacheProductAsync();

        return rstList;
    }

    /**
     * 给出一个ProductCharacter对象，通过递归调用获得以这个对象为Root的对象树，
     * 需要注意的是，Root的ParentProductCharacter需要在调用这个方法的地方设置（这个方法只负责向下的树）
     * 
     * @param _productCharacter_inDB
     * @return
     */
    private BProductCharacter getBProductCharacterTreeByDProductCharacter(ProductCharacter _productCharacter_inDB) {
        BProductCharacter bProductCharacter_treeRoot = null;
        if (_productCharacter_inDB == null) {
            return null;
        }

        ProductCharacterBDConvertor productCharacterBDConvertor = new ProductCharacterBDConvertor();

        // 基本属性
        bProductCharacter_treeRoot = (BProductCharacter) productCharacterBDConvertor.dtob(_productCharacter_inDB);

        // ParentProductCharacter不处理，在外层处理；在这里处理的话，会导致parent不一致

        // SubProductCharacters begin
        Set<BProductCharacter> subProductCharacters = new HashSet<BProductCharacter>();
        if (_productCharacter_inDB.getSubProductCharacters() != null && _productCharacter_inDB.getSubProductCharacters().iterator() != null) {
            bProductCharacter_treeRoot.setSubProductCharacters(new HashSet<BProductCharacter>());
            Iterator<ProductCharacter> itr_subProductCharacters_inDB = _productCharacter_inDB.getSubProductCharacters().iterator();
            while (itr_subProductCharacters_inDB.hasNext()) {
                BProductCharacter bSubProductCharacter = getBProductCharacterTreeByDProductCharacter(itr_subProductCharacters_inDB.next());
                bSubProductCharacter.setParentProductCharacter(bProductCharacter_treeRoot);
                subProductCharacters.add(bSubProductCharacter);
            }
        }
        if (subProductCharacters.size() > 0) {
            bProductCharacter_treeRoot.setSubProductCharacters(subProductCharacters);
        } else {
            if (bProductCharacter_treeRoot.getIsCatalog() == BizConst.GLOBAL_YESNO_NO) {
                bProductCharacter_treeRoot.setSubProductCharacters(null);
            }
        }
        // SubProductCharacters end
        return bProductCharacter_treeRoot;
    }

    // ProductCharacter end

    // Product begin
    public BProduct getProductTreeRoot4UI() throws Exception {
    	BProduct bProduct_treeRoot = this.getProductTreeRoot(); // 返回整个树形
        ServerEnvironment.getInstance().setBProductTreeRoot(bProduct_treeRoot);
        return ServerEnvironment.getInstance().getBProductTreeRoot();
    }

    /**
     * 新建 返回节点本身
     */
    public BProduct newProduct(BProduct _bProduct4new) throws Exception {
        BProduct bProduct_rst = null;

        ProductBDConvertor productBDConvertor = new ProductBDConvertor();
        Product product_new = (Product) productBDConvertor.btod(_bProduct4new, true);
        if (_bProduct4new.getParentProduct() != null) {
            Product parentProduct = (Product) productBDConvertor.btod(_bProduct4new.getParentProduct());
            product_new.setParentProduct(parentProduct);
        } else {
            product_new.setParentProduct(null);
        }

        Session session = HibernateSessionFactory.getSession();
        Transaction trsa = null;
        try {
            trsa = session.beginTransaction();
            DaoProduct daoProduct = new DaoProduct(session);

            // 检查父节点来测试保证父节点最新 begin
            if (_bProduct4new.getParentProduct() != null) {
                Product product_parent_inDB = daoProduct.getProductById(_bProduct4new.getParentProduct().getId());
                if (product_parent_inDB != null && product_parent_inDB.getVersion().longValue() != _bProduct4new.getParentProduct().getVersion().longValue()) {
                    Throwable cause = new Throwable(ExceptionConst.EXCEPTION_CAUSECODE_PARENTNODE_HAS_BEEN_MODIFIED);
                    Exception ex = new Exception(cause);
                    throw ex;
                }
            }
            // 检查父节点来测试保证父节点最新 end

            product_new = (Product) daoProduct.save(product_new);

            if (product_new.getProductProCharacters() != null && !(product_new.getProductProCharacters().isEmpty())) {
                DaoProductProCharacter daoProductProCharacter = new DaoProductProCharacter(session);
                Iterator<ProductProCharacter> itr_ProductProCharacters = product_new.getProductProCharacters().iterator();
                while (itr_ProductProCharacters.hasNext()) {
                    daoProductProCharacter.save(itr_ProductProCharacters.next());
                }
            }
            if (_bProduct4new.getParentProduct() == null)
                product_new.setPathCode("" + product_new.getId());
            else
                product_new.setPathCode(_bProduct4new.getParentProduct().getPathCode() + "-" + product_new.getId());
            daoProduct.save(product_new);
            trsa.commit();
        } catch (Exception ex) {
            if (trsa != null) {
                trsa.rollback();
            }
            ex.printStackTrace();
            throw ex;
        } finally {
            session.close();
        }

        // 若为套装产品， 解析套装结构并保存数据库
        if (BizConst.GLOBAL_YESNO_YES == _bProduct4new.getIsSuit()) {
            suitDm.updateProductSuit(product_new.getId(), suitSkuToSuitProductId(_bProduct4new.getSuitSkus())); 
        }

        // 主数据常驻内存，2011.01.15 by liuzhen begin
        /*
         * bProduct_rst = this.getProductTree( product_new.getId() );
         */
        BProduct bProduct_treeRoot = this.getProductTreeRoot(); // 返回整个树形
        ServerEnvironment.getInstance().setBProductTreeRoot(bProduct_treeRoot);
        bProduct_rst = ServerEnvironment.getInstance().getBProduct(product_new.getId());
        // 主数据常驻内存，2011.01.15 by liuzhen end
        return bProduct_rst;

    }

    /**
     * 返回整个树
     * 
     * @param _bProduct4upd
     * @return
     * @throws Exception
     */
    public BProduct updProduct(BProduct _bProduct4upd) throws Exception {
        if (_bProduct4upd == null) {
            Exception ex = new Exception("The object to update is a null object");
            throw ex;
        }

        String strKey4ppc = null;
        HashMap<String, ProductProCharacter> hmap_ProductProCharacter_inDB = new HashMap<String, ProductProCharacter>();

        boolean blIsValidYesToNo = false;
        int unitGroupChangeCase = BizConst.PRODUCT_UNITGROUP_CHANGECASE_NN2NNUNCHANGED; // 产品单位组变化情况
        int diffLayerChange = 0; // 产品层次变化情况
        List<Product> toUpdateProductList_descendent = new ArrayList<Product>();
        HashMap<Long, Integer> hmap_layerValue_old = new HashMap<Long, Integer>();

        Session querySession = HibernateSessionFactory.getSession();
        Transaction queryTrsa = null;
        try {
            queryTrsa = querySession.beginTransaction();
            DaoProduct daoProduct_query = new DaoProduct(querySession);
            Product product_inDB = daoProduct_query.getProductById(_bProduct4upd.getId());

            if (product_inDB == null) {
                Throwable cause = new Throwable(ExceptionConst.EXCEPTION_CAUSECODE_OBJECT_NOT_IN_DATABASE);
                Exception ex = new Exception(cause);
                throw ex;
            }

            // 数据库中产品特征情况 begin
            if (product_inDB.getProductProCharacters() != null && !(product_inDB.getProductProCharacters().isEmpty())) {
                Iterator<ProductProCharacter> itr_ProductProCharacters_inDB = product_inDB.getProductProCharacters().iterator();
                while (itr_ProductProCharacters_inDB.hasNext()) {
                    ProductProCharacter productProCharacter = itr_ProductProCharacters_inDB.next();
                    strKey4ppc = "" + productProCharacter.getProductCharacter().getId();

                    hmap_ProductProCharacter_inDB.put(strKey4ppc, productProCharacter);
                }
            }
            // 数据库中产品特征情况 end

            // 分析单位组变化情况 begin
            if (product_inDB.getUnitGroup() != null) {
                if (_bProduct4upd.getUnitGroup() != null) {
                    if (product_inDB.getUnitGroup().getId().longValue() == _bProduct4upd.getUnitGroup().getId().longValue()) {
                        unitGroupChangeCase = BizConst.PRODUCT_UNITGROUP_CHANGECASE_NN2NNUNCHANGED;
                    } else {
                        unitGroupChangeCase = BizConst.PRODUCT_UNITGROUP_CHANGECASE_NN2NNCHANGED;
                    }
                } else {
                    unitGroupChangeCase = BizConst.PRODUCT_UNITGROUP_CHANGECASE_NN2N;
                }
            } else {
                if (_bProduct4upd.getUnitGroup() != null) {
                    unitGroupChangeCase = BizConst.PRODUCT_UNITGROUP_CHANGECASE_N2NN;
                } else {
                    unitGroupChangeCase = BizConst.PRODUCT_UNITGROUP_CHANGECASE_N2N;
                }
            }
            // 分析单位组变化情况 end

            // 层次变化情况 begin
            diffLayerChange = _bProduct4upd.getProductLayer().getValue() - product_inDB.getProductLayer().getValue();
            // 层次变化情况 end

            // 是否有效变化情况 begin
            blIsValidYesToNo = false;
            if (product_inDB.getIsValid() == BizConst.GLOBAL_YESNO_YES && _bProduct4upd.getIsValid() == BizConst.GLOBAL_YESNO_NO) {
                blIsValidYesToNo = true;
            }
            // 是否有效变化情况 end

            if (_bProduct4upd.getIsCatalog() == BizConst.GLOBAL_YESNO_YES) {
                if (diffLayerChange != 0) {
                    toUpdateProductList_descendent = daoProduct_query.getDescendentProducts(product_inDB.getId(), false);
                    if (toUpdateProductList_descendent != null && !(toUpdateProductList_descendent.isEmpty())) {
                        for (int i = 0; i < toUpdateProductList_descendent.size(); i = i + 1) {
                            Product product_descendent = toUpdateProductList_descendent.get(i);
                            hmap_layerValue_old.put(product_descendent.getId(), product_descendent.getProductLayer().getValue());
                        }
                    }
                } else if (unitGroupChangeCase == BizConst.PRODUCT_UNITGROUP_CHANGECASE_NN2NNCHANGED
                        || unitGroupChangeCase == BizConst.PRODUCT_UNITGROUP_CHANGECASE_N2NN) {
                    // 产品的单位组有到有但变化、无到有，需要级联更新其下级产品
                    // 产品的单位组变化,则其单位组和单位被向下设置, 如果只是单位变化,而单位组不变化,不向下设置
                    toUpdateProductList_descendent = daoProduct_query.getDescendentProducts(product_inDB.getId(), false);
                } else if (blIsValidYesToNo == true) {
                    // 有效变无效，下级产品也要有效变无效。注意无效变有效时，不必这样级联处理。
                    // 逻辑上，无效产品目录下不能有有效的下级产品，但有效的产品目录下可以有无效的下级产品
                    toUpdateProductList_descendent = daoProduct_query.getDescendentProducts(product_inDB.getId(), false);
                }

                // 注意，这里只是读出来，还不能改。要放到下面的事务中一起修改
            }

            queryTrsa.commit();

        } catch (Exception ex) {
            if (queryTrsa != null) {
                queryTrsa.rollback();
            }
            ex.printStackTrace();
            throw ex;
        } finally {
            querySession.close();
        }

        // 更新套装属性
        suitDm.updateProductSuit(_bProduct4upd.getId(), this.suitSkuToSuitProductId(_bProduct4upd.getSuitSkus()));
        
        ProductBDConvertor productBDConvertor = new ProductBDConvertor();
        Product product_upd = productBDConvertor.btod(_bProduct4upd, true);
        if (_bProduct4upd.getParentProduct() != null) {
            Product uparentProduct = (Product) productBDConvertor.btod(_bProduct4upd.getParentProduct());
            product_upd.setParentProduct(uparentProduct);
            product_upd.setPathCode(_bProduct4upd.getParentProduct().getPathCode() + "-" + product_upd.getId());
        } else {
            product_upd.setParentProduct(null);
            product_upd.setPathCode("" + product_upd.getId());
        }

        // 参数中产品特征情况 begin
        HashMap<String, ProductProCharacter> hmap_ProductProCharacter_param = new HashMap<String, ProductProCharacter>();

        if (product_upd.getProductProCharacters() != null && !(product_upd.getProductProCharacters().isEmpty())) {
            Iterator<ProductProCharacter> itr_ProductProCharacters_param = product_upd.getProductProCharacters().iterator();
            while (itr_ProductProCharacters_param.hasNext()) {
                ProductProCharacter productProCharacter = itr_ProductProCharacters_param.next();
                strKey4ppc = "" + productProCharacter.getProductCharacter().getId();

                hmap_ProductProCharacter_param.put(strKey4ppc, productProCharacter);
            }
        }
        // 参数中产品特征情况 end

        // 分析特征分配情况 begin
        // productProCharacters begin
        List<ProductProCharacter> toDelProductProCharacterList = new ArrayList<ProductProCharacter>();
        List<ProductProCharacter> toUpdProductProCharacterList = new ArrayList<ProductProCharacter>();
        List<ProductProCharacter> toAddProductProCharacterList = new ArrayList<ProductProCharacter>();

        // param - inDB = add begin
        if (product_upd.getProductProCharacters() != null && !(product_upd.getProductProCharacters().isEmpty())) {
            Iterator<ProductProCharacter> itr_ProductProCharacters_param = product_upd.getProductProCharacters().iterator();
            while (itr_ProductProCharacters_param.hasNext()) {
                ProductProCharacter productProCharacter = itr_ProductProCharacters_param.next();
                strKey4ppc = "" + productProCharacter.getProductCharacter().getId();

                if (hmap_ProductProCharacter_inDB.get(strKey4ppc) == null) {
                    toAddProductProCharacterList.add(productProCharacter);
                }
            }
        }
        // param - inDB = add end

        // inDB - param = del begin
        if (hmap_ProductProCharacter_inDB.values() != null && !(hmap_ProductProCharacter_inDB.values().isEmpty())) {
            Iterator<ProductProCharacter> itr_ProductProCharacters_inDB = hmap_ProductProCharacter_inDB.values().iterator();
            while (itr_ProductProCharacters_inDB.hasNext()) {
                ProductProCharacter productProCharacter = itr_ProductProCharacters_inDB.next();
                strKey4ppc = "" + productProCharacter.getProductCharacter().getId();

                if (hmap_ProductProCharacter_param.get(strKey4ppc) == null) {
                    toDelProductProCharacterList.add(productProCharacter);
                }
            }
        }
        // inDB - param = del end
        // productProCharacters end
        // 分析特征分配情况 end

        Session session = HibernateSessionFactory.getSession();
        Transaction trsa = null;
        try {
            trsa = session.beginTransaction();
            DaoProduct daoProduct = new DaoProduct(session);
            daoProduct.update(product_upd);
            // 更新特征分配情况 begin
            DaoProductProCharacter daoProductProCharacter = new DaoProductProCharacter(session);
            for (int i = 0; i < toDelProductProCharacterList.size(); i++) {
                daoProductProCharacter.delete(toDelProductProCharacterList.get(i));
            }
            for (int i = 0; i < toUpdProductProCharacterList.size(); i++) {
                daoProductProCharacter.update(toUpdProductProCharacterList.get(i));
            }
            for (int i = 0; i < toAddProductProCharacterList.size(); i++) {
                daoProductProCharacter.save(toAddProductProCharacterList.get(i));
            }
            // 更新特征分配情况 end

            // 更新下级产品(组) begin
            if (toUpdateProductList_descendent != null && !(toUpdateProductList_descendent.isEmpty())) {
                DaoProductLayer daoProductLayer = new DaoProductLayer(session);
                for (int i = 0; i < toUpdateProductList_descendent.size(); i++) {
                    Product product_upd_descendent = toUpdateProductList_descendent.get(i);

                    if (diffLayerChange != 0) {
                        int newLayerValue = hmap_layerValue_old.get(product_upd_descendent.getId()) + diffLayerChange;
                        ProductLayer productLayer = daoProductLayer.getProductLayerByValue(newLayerValue);
                        if (productLayer == null) {
                            Throwable cause = new Throwable(ExceptionConst.EXCEPTION_CAUSECODE_PRODUCTLAYER_MAXLAYER);
                            Exception ex = new Exception(cause);
                            throw ex;
                        } else {
                            product_upd_descendent.setProductLayer(productLayer);
                        }
                    }

                    if (unitGroupChangeCase == BizConst.PRODUCT_UNITGROUP_CHANGECASE_NN2NNCHANGED
                            || unitGroupChangeCase == BizConst.PRODUCT_UNITGROUP_CHANGECASE_N2NN) {
                        product_upd_descendent.setUnitGroup(product_upd.getUnitGroup());
                        product_upd_descendent.setUnit(product_upd.getUnit());
                    }

                    if (blIsValidYesToNo == true) {
                        product_upd_descendent.setIsValid(BizConst.GLOBAL_YESNO_NO);
                    }

                    daoProduct.update(product_upd_descendent);
                }
            }

            // 更新下级产品(组) end

            trsa.commit();
        } catch (Exception ex) {
            if (trsa != null) {
                trsa.rollback();
            }
            ex.printStackTrace();
            throw ex;
        } finally {
            session.close();
        }

        BProduct bProduct_rst = this.getProductTreeRoot(); // 返回整个树形
        // 主数据常驻内存，2011.01.15 by liuzhen begin
        ServerEnvironment.getInstance().setBProductTreeRoot(bProduct_rst);
        // 主数据常驻内存，2011.01.15 by liuzhen end
        return bProduct_rst;
    }

    public boolean delProduct(BProduct _bProduct4del) throws Exception {
        if (_bProduct4del == null) {
            Exception ex = new Exception("The object to delete is null! Do nothing!");
            throw ex;
        }

        ProductBDConvertor productBDConvertor = new ProductBDConvertor();
        Product product_del = (Product) productBDConvertor.btod(_bProduct4del);

        Session session = HibernateSessionFactory.getSession();
        Transaction trsa = null;
        try {
            trsa = session.beginTransaction();
            DaoProduct daoProduct = new DaoProduct(session);
            // 数据库设置成绩级联删除
            // daoProduct.delete( product_del );
            if (ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_ORACLE)) {
                daoProduct.delete(product_del);
            } else if (ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_SQLSERVER) )
            {
                List<Product> listProducts = daoProduct.getSubProducts(product_del);
                for (Product _product : listProducts) {
                    daoProduct.delete(_product);
                }
                daoProduct.delete(product_del);
            }
            trsa.commit();
        } catch (Exception ex) {
            if (trsa != null) {
                trsa.rollback();
            }
            ex.printStackTrace();
            throw ex;
        } finally {
            session.close();
        }

        // 主数据常驻内存，2011.01.15 by liuzhen begin
        BProduct bProduct_treeRoot = this.getProductTreeRoot(); // 返回整个树形
        ServerEnvironment.getInstance().setBProductTreeRoot(bProduct_treeRoot);
        // 主数据常驻内存，2011.01.15 by liuzhen end

        return true;
    }

    /**
     * 返回整个产品树结构 从数据库读取 Product 的唯一入口，其他的都是用 内存常驻变量
     * 
     * @return
     * @throws Exception
     */
    public BProduct getProductTreeRoot() throws Exception {
    	
    	logger.info("开始获取整个产品树...");
    	long start = System.currentTimeMillis();
        BProduct bProduct_treeRoot = null;

        Session session = HibernateSessionFactory.getSession();
        Transaction trsa = null;
        try {
            trsa = session.beginTransaction();
            DaoProduct daoProduct = new DaoProduct(session);
            Product rootProduct_inDB = null;
            rootProduct_inDB = daoProduct.getProductTreeRoot();

            if (rootProduct_inDB != null) {
                bProduct_treeRoot = this.getBProductTreeByDProduct(rootProduct_inDB);
                bProduct_treeRoot.setParentProduct(null);
            }

            trsa.commit();
        } catch (Exception ex) {
            if (trsa != null) {
                trsa.rollback();
            }
            ex.printStackTrace();
            throw ex;
        } finally {
            session.close();
        }
        
        logger.info("获取产品树结束，耗时[" + (System.currentTimeMillis() - start) + "]ms");

        return bProduct_treeRoot;

    }

    /**
     * 产品导入
     * 
     * @param _list4ABImProduct
     * @return
     * @throws Exception
     */
    public List<ABImProduct> importProduct(List<ABImProduct> _list4ABImProduct) throws Exception {
    	List<ABImProduct> rstList = new ArrayList<ABImProduct>();
    	try{

            logger.info("产品数据导入开始...");
            long beginTime = System.currentTimeMillis();
            int impNumber = 0;
            
            if (_list4ABImProduct == null || _list4ABImProduct.isEmpty()) {
                throw new Exception("Paramete is not correct");
            }

            
            ABImProduct abImProduct = null;
            String importResult = null;
            Session session = HibernateSessionFactory.getSession();
            DaoProduct daoProduct = new DaoProduct(session);
            DaoProductLayer daoProductLayer = new DaoProductLayer(session);
            DaoUnitGroup daoUnitGroup = new DaoUnitGroup(session);
            
            for (int i = 0; i < _list4ABImProduct.size(); i++) {
            	importResult = "";
                // 逐行处理
                abImProduct = _list4ABImProduct.get(i);

                Transaction trsa = null;
                try {
                    
                	trsa = session.beginTransaction();
                    // 建立新 Product begin
                    Product newProduct = new Product();
                    Product oldProduct = null;//产品CODE数据是否已存在
                    try{
                    	//验证产品有效性，并填充Product对象
                    	oldProduct = validatorImpProduct(abImProduct, newProduct, daoProduct, daoProductLayer, daoUnitGroup);
                    }catch (Exception e) { 
                    	abImProduct.setImportResult(e.getMessage());
                        rstList.add(abImProduct);
                        logger.error("产品["+abImProduct.getCode()+"]导入异常",e);
                        continue;
    				}
                    
                    Long productId = null;
                    int isSuit = 0;
                    if( oldProduct == null ){
                    	newProduct = (Product) daoProduct.save(newProduct);
                        trsa.commit();
                        productId = newProduct.getId();
                        isSuit = newProduct.getIsSuit();
                    }else{
                    	productId = oldProduct.getId();
                    	// 套装属性
                        if ("0".equals(abImProduct.getIsSuit()) || "1".equals(abImProduct.getIsSuit())) {
                            isSuit = Integer.parseInt(abImProduct.getIsSuit());
                        } else {
                            throw new Exception("Fail: IsSuit is not 0 or 1 ");
                        }
                    }
                    
                    // 新建套装关系
                    if (BizConst.GLOBAL_YESNO_YES == isSuit ) {
                    	Map<Long, Integer> productIdMap = suitDm.suitCodeToSuitProductId(abImProduct.getSuitDetailMap());
                        suitDm.updateProductSuit(productId, productIdMap );
                        logger.debug("产品["+abImProduct.getCode()+"]套装，共有[" + productIdMap.size()+ "]个组件.");
                    }

                    // 导入成功 begin
                    abImProduct.setImportResult(BizConst.IMPORT_RESULT_SUCCESS);
                    impNumber ++;
                    
                    dmo.commit(DMOConst.DS_DEFAULT);
                    
                    rstList.add(abImProduct);
                    // 导入成功 end

                } catch (Exception ex) {
                	dmo.rollback(DMOConst.DS_DEFAULT);
                    if (trsa != null && trsa.isActive() ) {
                        trsa.rollback();
                    }
                    ex.printStackTrace();
                    logger.error("产品导入异常", ex);
                    if (ex.getCause() != null) {
                        importResult = "Fail: " + ex.getCause().getMessage();
                    } else {
                        importResult = "Fail: " + ex.getMessage();
                    }
                    abImProduct.setImportResult(importResult);
                    rstList.add(abImProduct);
                }finally{
                	
                }
            }
            
            session.close();
            
            long curTime = System.currentTimeMillis();
            logger.info("产品数据导入成功!共处理[" + impNumber + "]条数据，耗时[" + (curTime - beginTime) + "]ms");

            logger.info("开始刷新产品主数据到内存！");
            // 主数据常驻内存
    		BProduct bProduct_rst = getProductTreeRoot(); // 返回整个树形
    		ServerEnvironment.getInstance().setBProductTreeRoot(bProduct_rst);
    		logger.info("刷新产品主数据到内存OK");
            
            return rstList;
    	}catch (Exception e) {
			e.printStackTrace();
			logger.error("产品导入出现异常",e);
			throw e;
		}
    }
    
    /**
     * 验证导入的产品数据的合法性,如果产品CODE已存在，返回true
     * @param abImProduct
     * @throws Exception
     */
    private Product validatorImpProduct(ABImProduct abImProduct,Product newProduct,DaoProduct daoProduct
    						,DaoProductLayer daoProductLayer,DaoUnitGroup daoUnitGroup) throws Exception{
    	 Product parentProduct = null;
         ProductLayer productLayer = null;
         UnitGroup unitGroup = null;
         Unit unit = null;
         String code = null;
         String name = null;
         int isCatalog;
         int isValid;
         String description = null;
         String comments = null;
         int isSuit = 0, shelfLife = 0, purchaseLeadTime = 0, withdrawLeadTime = 0;
         
         // 套装属性
         if ("0".equals(abImProduct.getIsSuit()) || "1".equals(abImProduct.getIsSuit())) {
             isSuit = Integer.parseInt(abImProduct.getIsSuit());
         } else {
             throw new Exception("Fail: IsSuit is not 0 or 1 ");
         }
         
    	// 编码
        code = null;
        if (abImProduct.getCode() == null || abImProduct.getCode().trim().equals("")) {
            throw new Exception("Fail: code is null ");
        }
        code = abImProduct.getCode().trim();
        
        boolean isExist = false; //数据是否已经存在
        //数据是否已存在
        String sqlRestriction = "code = '" + code + "'";
        List<Product> listProductInDB = daoProduct.getProducts(sqlRestriction);
        if (listProductInDB != null && listProductInDB.size() > 0) {
        	//throw new Exception("Fail: fine the same product by code ");
        	isExist = true;
        	return listProductInDB.get(0);
        }

        // 上级产品
        parentProduct = null;
        if (abImProduct.getParentCode() == null || abImProduct.getParentCode().trim().equals("")) {
            throw new Exception("Fail: parentCode is null");
        }
        
        sqlRestriction = "code = '" + abImProduct.getParentCode().trim() + "'";
        List<Product> listProduct_inDB = daoProduct.getProducts(sqlRestriction);
        if (listProduct_inDB == null || listProduct_inDB.isEmpty()) {
            throw new Exception("Fail: can not find parent product");
        }
        
        if (listProduct_inDB.size() > 1) {
            throw new Exception("Fail: find multiple parent product");
        }
        
        parentProduct = listProduct_inDB.get(0);
        if (parentProduct.getIsCatalog() == BizConst.GLOBAL_YESNO_NO) {
            throw new Exception("Fail: parent product is not a catalog");
        }

        // 层次
        productLayer = null;
        if (abImProduct.getLayerValue() == null || abImProduct.getLayerValue().trim().equals("")) {
            throw new Exception("Fail: layerValue is null");
        }

        int layerValue = 0;
        try {
            layerValue = Integer.parseInt(abImProduct.getLayerValue().trim());
        } catch (Exception ex) {
            throw new Exception("Fail: layerValue is not valid integer");
        }

        productLayer = daoProductLayer.getProductLayerByValue(layerValue);
        if (productLayer == null) {
            throw new Exception("Fail: can not find productLayer");
        }

        if (productLayer.getValue() != parentProduct.getProductLayer().getValue() + 1) {
            throw new Exception("Fail: productLayer value != parent  productLayer value + 1");
        }

        // 单位组
        unitGroup = null;
        unit = null;

        String strUnitGroupCode = abImProduct.getUnitGroupCode();
        if (strUnitGroupCode != null) {
            strUnitGroupCode = strUnitGroupCode.trim();
            if (strUnitGroupCode.equals("")) {
                strUnitGroupCode = null;
            }
        }

        String strUnitCode = abImProduct.getUnitCode();
        if (strUnitCode != null) {
            strUnitCode = strUnitCode.trim();
            if (strUnitCode.equals("")) {
                strUnitCode = null;
            }
        }

        if ((strUnitGroupCode != null && strUnitCode == null)) {
            throw new Exception("Fail: UnitGroupCode is not null while UnitCode is null ");
        }

        if ((strUnitGroupCode == null && strUnitCode != null)) {
            throw new Exception("Fail: UnitGroupCode is null while UnitCode is not null ");
        }

        if (parentProduct.getUnitGroup() != null && strUnitGroupCode == null) {
            throw new Exception("Fail: UnitGroupCode is null while parent product unitgroup is not null ");
        }

        if (abImProduct.getIsCatalog().equals("0") && !parentProduct.getUnitGroup().getCode().equals(abImProduct.getUnitGroupCode())) {
            throw new Exception("Fail: UnitGroupCode is not equals parent product's unitgroup ");
        }

        sqlRestriction = "code = '" + strUnitGroupCode + "'";
        List<UnitGroup> listUnitGroup_inDB = daoUnitGroup.getUnitGroups(sqlRestriction, -1, 3);
        if (listUnitGroup_inDB == null || listUnitGroup_inDB.isEmpty()) {
            throw new Exception("Fail: can not find unitGroup ");
        }
        if (listUnitGroup_inDB.size() > 1) {
            throw new Exception("Fail: find multiple unitGroup ");
        }
        unitGroup = listUnitGroup_inDB.get(0);

        //单位
        for(Unit tmpUnit : unitGroup.getUnits()) {
            if (tmpUnit.getCode().equals(strUnitCode)) {
                unit = tmpUnit;
                break;
            }
        }
        
        if (unit == null) {
            throw new Exception("Fail: can not find unit ");
        }

        // 名称
        name = null;
        if (abImProduct.getName() == null || abImProduct.getName().trim().equals("")) {
            throw new Exception("Fail: name is null ");
        }
        name = abImProduct.getName().trim();

        // 是否目录
        isCatalog = 0;
        if (abImProduct.getIsCatalog() == null || abImProduct.getIsCatalog().trim().equals("")) {
            throw new Exception("Fail: isCatalog is null ");
        }
        if (abImProduct.getIsCatalog().trim().equals("0")) {
            isCatalog = 0;
        } else if (abImProduct.getIsCatalog().trim().equals("1")) {
            isCatalog = 1;
        } else {
            throw new Exception("Fail: isCatalog is not 0 or 1 ");
        }

        // 是否有效
        isValid = 0;
        if (abImProduct.getIsValid() == null || abImProduct.getIsValid().trim().equals("")) {
            throw new Exception("Fail: IsValid is null ");
        }
        if (abImProduct.getIsValid().trim().equals("0")) {
            isValid = 0;
        } else if (abImProduct.getIsValid().trim().equals("1")) {
            isValid = 1;
        } else {
            throw new Exception("Fail: IsValid is not 0 or 1 ");
        }

        if (parentProduct.getIsValid() == BizConst.GLOBAL_YESNO_NO && isValid == BizConst.GLOBAL_YESNO_YES) {
            throw new Exception("Fail: product is Valid  while parent product is not valid ");
        }

        // description
        description = null;
        if (abImProduct.getDescription() != null) {
            description = abImProduct.getDescription().trim();
        }

        // comments
        comments = null;
        if (abImProduct.getComments() != null) {
            comments = abImProduct.getComments().trim();
        }
        
        // 套装产品详情, 可以允许在不初始化详情的情况下导入套装
//        if (BizConst.GLOBAL_YESNO_YES == isSuit && abImProduct.getSuitDetailMap().size() == 0) {
//            throw new Exception("Fail: Product is suit, but SuitDetail is empty ");
//        }
        
        // 过期日期
        try {
            shelfLife = Integer.parseInt(abImProduct.getShelfLife());
            if (shelfLife < 0) {
                throw new Exception("Fail : ShelfLife is not vaild, should more than 0");
            }
        } catch (Exception e) {
            throw new Exception("Fail: ShelfLife is not valid Integer");
        } 
        
        // 采购提前期
        try {
            purchaseLeadTime = Integer.parseInt(abImProduct.getPurchaseLeadTime());
            if (purchaseLeadTime < 0) {
                throw new Exception("Fail : PurchaseLeadTime is not vaild, should more than 0");
            }
        } catch (Exception e) {
            throw new Exception("Fail: PurchaseLeadTime is not valid Integer");
        }
        
        // 提前下架期
        try {
            withdrawLeadTime = Integer.parseInt(abImProduct.getWithdrawLeadTime());
            if (withdrawLeadTime < 0) {
                throw new Exception("Fail : WithdrawLeadTime is not vaild, should more than 0");
            }
        } catch (Exception e) {
            throw new Exception("Fail: WithdrawLeadTime is not valid Integer");
        }
        
        newProduct.setParentProduct(parentProduct);
        newProduct.setProductLayer(productLayer);
        newProduct.setUnitGroup(unitGroup);
        newProduct.setUnit(unit);
        newProduct.setCode(code);
        newProduct.setName(name);
        newProduct.setIsCatalog(isCatalog);
        newProduct.setIsValid(isValid);
        newProduct.setDescription(description);
        newProduct.setComments(comments);
        newProduct.setIsSuit(isSuit);
        newProduct.setShelfLife(shelfLife);
        newProduct.setPurchaseLeadTime(purchaseLeadTime);
        newProduct.setWithdrawLeadTime(withdrawLeadTime);
        
        return null;
    }

    /**
     * 产品-产品特征导入
     * 
     * @param _list4ABImProductProCharacter
     * @return
     * @throws Exception
     */
    public List<ABImProductProCharacter> importProductProCharacter(List<ABImProductProCharacter> _list4ABImProductProCharacter) throws Exception {
        List<ABImProductProCharacter> rstList = new ArrayList<ABImProductProCharacter>();

        if (_list4ABImProductProCharacter == null || _list4ABImProductProCharacter.isEmpty()) {
            throw new Exception("Paramete is not correct");
        }

        ABImProductProCharacter abImProductProCharacter = null;
        String importResult = null;

        for (int i = 0; i < _list4ABImProductProCharacter.size(); i++) {
            // 逐行处理
            abImProductProCharacter = _list4ABImProductProCharacter.get(i);

            Session session = HibernateSessionFactory.getSession();
            Transaction trsa = null;
            try {
                trsa = session.beginTransaction();

                DaoProductProCharacter daoProductProCharacter = new DaoProductProCharacter(session);
                DaoProduct daoProduct = new DaoProduct(session);
                DaoProductCharacter daoProductCharacter = new DaoProductCharacter(session);

                // 明细产品
                Product detailProduct = null;
                if (abImProductProCharacter.getDetailProductCode() == null || abImProductProCharacter.getDetailProductCode().trim().equals("")) {
                    importResult = "Fail: detailProductCode is null";
                    abImProductProCharacter.setImportResult(importResult);

                    rstList.add(abImProductProCharacter);
                    continue;
                }

                detailProduct = daoProduct.getDetailProductByCode(abImProductProCharacter.getDetailProductCode().trim());
                if (detailProduct == null) {
                    importResult = "Fail: can not find detailProduct";
                    abImProductProCharacter.setImportResult(importResult);

                    rstList.add(abImProductProCharacter);
                    continue;
                }

                // 明细产品特征
                ProductCharacter detailProductCharacter = null;
                if (abImProductProCharacter.getDetailProductCharacterCode() == null
                        || abImProductProCharacter.getDetailProductCharacterCode().trim().equals("")) {
                    importResult = "Fail: detailProductCharacterCode is null";
                    abImProductProCharacter.setImportResult(importResult);

                    rstList.add(abImProductProCharacter);
                    continue;
                }

                detailProductCharacter = daoProductCharacter.getDetialProductCharacterByCode(abImProductProCharacter.getDetailProductCharacterCode().trim());
                if (detailProductCharacter == null) {
                    importResult = "Fail: can not find detailProductCharacter";
                    abImProductProCharacter.setImportResult(importResult);

                    rstList.add(abImProductProCharacter);
                    continue;
                }

                // 判断同一特征类型是否已经存在
                List<String> listProductCharacterTyps = new ArrayList<String>();// 用来存放明细产品已经关联了的产品特征类型
                for (ProductProCharacter productProCharacter : detailProduct.getProductProCharacters()) {
                    listProductCharacterTyps.add(productProCharacter.getProductCharacter().getType());
                }
                if (listProductCharacterTyps.contains(detailProductCharacter.getType())) {
                    importResult = ExceptionConst.EXCEPTION_CAUSECODE_PRODUCTPROCHARACTER_SAMECHARACTERTYPE;
                    abImProductProCharacter.setImportResult(importResult);

                    rstList.add(abImProductProCharacter);
                    continue;
                }

                // 建立新 ProductProCharacter begin
                ProductProCharacter newProductProCharacter = new ProductProCharacter();

                newProductProCharacter.setProduct(detailProduct);
                newProductProCharacter.setProductCharacter(detailProductCharacter);

                daoProductProCharacter.save(newProductProCharacter);
                // 建立新 ProductProCharacter end

                trsa.commit();

                // 导入成功 begin
                importResult = BizConst.IMPORT_RESULT_SUCCESS;
                abImProductProCharacter.setImportResult(importResult);

                rstList.add(abImProductProCharacter);
                // 导入成功 end

            } catch (Exception ex) {
                if (trsa != null) {
                    trsa.rollback();
                }
                ex.printStackTrace();

                if (ex.getCause() != null) {
                    importResult = "Fail: " + ex.getCause().getMessage();
                } else {
                    importResult = "Fail: " + ex.getMessage();
                }

                abImProductProCharacter.setImportResult(importResult);

                rstList.add(abImProductProCharacter);

            } finally {
                session.close();
            }
        }

        return rstList;
    }

    /**
     * 给出一个Product对象，通过递归调用获得以这个对象为Root的对象树，
     * 需要注意的是，Root的ParentProduct需要在调用这个方法的地方设置（这个方法只负责向下的树）
     * 
     * @param _product_inDB
     * @return
     */
    private BProduct getBProductTreeByDProduct(Product _product_inDB) {
        BProduct bProduct_treeRoot = null;
        if (_product_inDB == null) {
            return null;
        }

        ProductBDConvertor productBDConvertor = new ProductBDConvertor();

        // 基本属性
        bProduct_treeRoot = (BProduct) productBDConvertor.dtob(_product_inDB, true);

        // ParentProduct不处理，在外层处理；在这里处理的话，会导致parent不一致

        // SubProducts begin
        Set<BProduct> subProducts = new HashSet<BProduct>();
        if (_product_inDB.getSubProducts() != null && _product_inDB.getSubProducts().iterator() != null) {
            bProduct_treeRoot.setSubProducts(new HashSet<BProduct>());
            Iterator<Product> itr_subProducts_inDB = _product_inDB.getSubProducts().iterator();
            while (itr_subProducts_inDB.hasNext()) {
                BProduct bSubProduct = getBProductTreeByDProduct(itr_subProducts_inDB.next());
                bSubProduct.setParentProduct(bProduct_treeRoot);
                subProducts.add(bSubProduct);
            }
        }
        if (subProducts.size() > 0) {
            bProduct_treeRoot.setSubProducts(subProducts);
        } else {
            if (bProduct_treeRoot.getIsCatalog() == BizConst.GLOBAL_YESNO_NO) {
                bProduct_treeRoot.setSubProducts(null);
            }
        }
        // SubProducts end
        return bProduct_treeRoot;
    }

    /**
     * 根据编码查询产品，返回对象只保留了基本信息,ID,CODE,NAME
     * 
     * @param proCode
     * @return
     */
    public BProduct getProductByCode(String proCode) {
        BProduct product = new BProduct();
        String sql = "select id,code,name  from product where code =? and iscatalog = 0";
        try {
            HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sql, proCode);
            if (vos == null || vos.length < 1 || vos.length > 1) // 查询不到或者查询结果超过1个，都视为无效
                return null;
            else if (vos.length == 1) {
                HashVO vo = vos[0];
                product.setId(vo.getLognValue("ID"));
                product.setName(vo.getStringValue("NAME"));
                product.setCode(vo.getStringValue("CODE"));
            }
        } catch (Exception e) {
            logger.error("根据产品编码查询产品异常:" + e.toString());
        } 

        return product;
    }
    
    /**
     * 根据编码查询产品，返回对象只保留了基本信息,ID,CODE,NAME
     * 
     * @param proCode
     * @return
     */
    public List<BProduct> getProductsByCodeOrName(String proCode,String proName) throws Exception {
        List<BProduct> proList = null;
    	
        String sql = "select ID,CODE,NAME,ISCATALOG,IS_SUIT,SHELF_LIFE,WITHDRAW_LEAD_TIME,PURCHASE_LEAD_TIME,ISVALID from product where iscatalog = 0 and isvalid=1 ";
        if(StringUtils.isNotEmpty(proCode)){
        	sql += " and code like '%" + proCode + "%'";
        }
    	if(StringUtils.isNotEmpty(proName)){
        	sql += " and name like '%" + proName + "%'";
        }

    	HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sql);
        proList = new ArrayList<BProduct>(vos.length);
        for(HashVO vo : vos){
        	BProduct product = new BProduct();
            product.setId(vo.getLognValue("ID"));
            product.setName(vo.getStringValue("NAME"));
            product.setCode(vo.getStringValue("CODE"));
            product.setIsCatalog(vo.getIntegerValue("ISCATALOG"));
            product.setIsSuit(vo.getIntegerValue("IS_SUIT"));
            product.setShelfLife(vo.getIntegerValue("SHELF_LIFE"));
            product.setWithdrawLeadTime(vo.getIntegerValue("WITHDRAW_LEAD_TIME"));
            product.setPurchaseLeadTime(vo.getIntegerValue("PURCHASE_LEAD_TIME"));
            product.setIsValid(vo.getIntegerValue("ISVALID"));
            
            proList.add(product);
        }

        return proList;
    }

    /**
     * 刷新产品缓存
     * 
     * @throws Exception
     */
	private void refreshCacheProductAsync() throws Exception {

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					long start = System.currentTimeMillis();
					// 主数据常驻内存
					BProduct bProduct_rst = getProductTreeRoot(); // 返回整个树形
					ServerEnvironment.getInstance().setBProductTreeRoot(bProduct_rst);
					long end = System.currentTimeMillis();
					
					logger.info("产品数据导入后，刷新产品缓存,耗时[" + (end - start) + "]ms");
					
				} catch (Exception e) {
					CoolLogger.getLogger().error("异步刷新产品缓存失败", e);
				}
			}
		}).start();

	}

    /**
     * 刷新产品特征缓存
     * 
     * @throws Exception
     */
    private void refreshCacheProductCharacter() throws Exception {
        BProductCharacter bProductCharacter_treeRoot = this.getProductCharacterTreeRoot(); // 返回整个树形
        ServerEnvironment.getInstance().setBProductCharacterTreeRoot(bProductCharacter_treeRoot);
    }
    
    /**
     * 将suit sku集合信息转换为 id:number
     */
    private Map<Long, Integer> suitSkuToSuitProductId(Set<BSuitSkuRel> suitSkus) {
        Map<Long, Integer> suitProdictIdMap = new HashMap<Long, Integer>();
        if (CollectionUtils.isNotEmpty(suitSkus)) {
            for (BSuitSkuRel suitSku : suitSkus) {
                suitProdictIdMap.put(suitSku.getProId(), suitSku.getRatio());
            }
        }

        return suitProdictIdMap;
    }
    
    /** 获取套装关系 */
    public List<BSuitSkuRel> getSuitSkus(Long suitId) throws Exception {
    	return suitDm.getSuitSkus(suitId);
    }
    
    //query product report
    public List<Map<String,Object>> getProductDataReport(Map<String,String> queryCond) throws Exception{
    	String proCode = queryCond.get("proCode");
    	String proName = queryCond.get("proName");
    	String proLayer = queryCond.get("proLayer");
    	String isValid = queryCond.get("isValid");
    	String isSuit = queryCond.get("isSuit");
    	
    	String sqlRestriction = "";
    	if(StringUtils.isNotEmpty(proCode)){
    		sqlRestriction += " and p.code like '%" + proCode + "%'";
        }
    	if(StringUtils.isNotEmpty(proName)){
    		sqlRestriction += " and p.name like '%" + proName + "%'";
        }
    	
    	if(StringUtils.isNotEmpty(proLayer)){
    		sqlRestriction += " and p.productlayerid = '" + proLayer + "'";
    	}
    	
    	if(StringUtils.isNotEmpty(isValid)){
    		sqlRestriction += " and p.isvalid = '" + isValid + "'";
    	}
    	
    	if(StringUtils.isNotEmpty(isSuit)){
    		sqlRestriction += " and p.is_suit = '" + isSuit + "'";
    	}
    	
    	String sql = "SELECT P.ID,P.CODE,P.NAME,P.ISCATALOG,P.ISVALID,P.DESCRIPTION,P.COMMENTS,P.IS_SUIT,P.SHELF_LIFE,P.PURCHASE_LEAD_TIME"
        	+" ,P.WITHDRAW_LEAD_TIME,P.PRODUCTLAYERID,P.UNITGROUPID,P.UNITID,P.PARENTPRODUCTID,P.PATHCODE,P.BASEUNITID"
        	+" ,PL.VALUE LAYER_VALUE,PL.DESCRIPTION LAYER_NAME"
        	+" ,UG.CODE UNITGROUP_CODE,U.CODE UNIT_CODE,PP.CODE PARENT_CODE,PP.NAME PARENT_NAME"
        	//拼装套装产品的子产品
        	+" ,stuff((select '|'+C.CODE+':'+cast(S.PRODUCT_NUMBER as varchar) from PRODUCT_SUIT S,PRODUCT C where C.ID=S.PRODUCT_ID AND S.SUIT_PRODUCT_ID=P.ID for xml path('')), 1, 1, '') SUITDETAIL"
        	+" FROM PRODUCT P,PRODUCTLAYER PL,UNITGROUP UG,UNIT U,PRODUCT PP"
        	+" WHERE PL.ID=P.PRODUCTLAYERID AND UG.ID=P.UNITGROUPID AND U.ID=P.UNITID AND PP.ID=P.PARENTPRODUCTID ";
        
        sql += sqlRestriction;
        
        HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sql);
        List<Map<String,Object>> vosList = HashVoUtil.hashVosToMapList(vos);
        
        return vosList;
        
    }
    
}

/**************************************************************************
 * 
 * $RCSfile: ProductService.java,v $ $Revision: 1.4 $ $Date: 2010/08/08 02:51:22
 * $
 * 
 * $Log: ProductService.java,v $ Revision 1.4 2010/08/08 02:51:22 liuzhen
 * 2010.08.08 by liuzhen
 * 
 * Revision 1.1 2010/07/04 07:26:53 liuzhen 2010.07.04 by liuzhen Committed on
 * the Free edition of March Hare Software CVSNT Server. Upgrade to CVS Suite
 * for more features and support: http://march-hare.com/cvsnt/
 * 
 * Revision
 * 
 * 
 ***************************************************************************/
