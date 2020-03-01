/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import java.util.Iterator;

import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.dataaccess.bizobject.BProduct;
import dmdd.dmddjava.dataaccess.bizobject.BProductCharacter;
import dmdd.dmddjava.dataaccess.bizobject.BProductLayer;
import dmdd.dmddjava.dataaccess.bizobject.BProductProCharacter;
import dmdd.dmddjava.dataaccess.bizobject.BUnit;
import dmdd.dmddjava.dataaccess.bizobject.BUnitGroup;
import dmdd.dmddjava.dataaccess.dataobject.Product;
import dmdd.dmddjava.dataaccess.dataobject.ProductCharacter;
import dmdd.dmddjava.dataaccess.dataobject.ProductLayer;
import dmdd.dmddjava.dataaccess.dataobject.ProductProCharacter;
import dmdd.dmddjava.dataaccess.dataobject.Unit;
import dmdd.dmddjava.dataaccess.dataobject.UnitGroup;

/**
 * @author liuzhen
 * 
 */
public class ProductBDConvertor implements BDConvertorInterface {

    /**
	 * 
	 */
    public ProductBDConvertor() {
        // TODO Auto-generated constructor stub
    }

    /*
     * (non-Javadoc) 基本属性,处理; 引用的对象属性unitGroup、productLayer,处理;
     * 引用的对象属性parentProduct,不处理; 下附的集合属性productUnits、productProCharacters,不处理;
     * 下附的集合属性subProducts,不处理
     * 
     * @see
     * dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang
     * .Object, java.lang.Object)
     */
    public void btod(Object b_obj, Object d_obj) {
        BProduct bProduct = null;
        Product product = null;

        if (b_obj == null) {
            bProduct = new BProduct();
        } else {
            bProduct = (BProduct) b_obj;
        }

        if (d_obj == null) {
            return;
        } else {
            product = (Product) d_obj;
        }

        product.setVersion(bProduct.getVersion());
        product.setId(bProduct.getId());
        product.setCode(bProduct.getCode());
        product.setName(bProduct.getName());
        product.setIsCatalog(bProduct.getIsCatalog());
        product.setIsValid(bProduct.getIsValid());
        product.setDescription(bProduct.getDescription());
        product.setComments(bProduct.getComments());
        product.setPathCode(bProduct.getPathCode());
        product.setIsSuit(bProduct.getIsSuit());
        product.setShelfLife(bProduct.getShelfLife());
        product.setPurchaseLeadTime(bProduct.getPurchaseLeadTime());
        product.setWithdrawLeadTime(bProduct.getWithdrawLeadTime());
        
        // productLayer
        if (bProduct.getProductLayer() != null) {
            ProductLayerBDConvertor productLayerBDConvertor = new ProductLayerBDConvertor();
            ProductLayer productLayer = (ProductLayer) productLayerBDConvertor.btod(bProduct.getProductLayer());
            product.setProductLayer(productLayer);
        } else {
            product.setProductLayer(null);
        }
        // unitGroup
        if (bProduct.getUnitGroup() != null) {
            UnitGroupBDConvertor unitGroupBDConvertor = new UnitGroupBDConvertor();
            UnitGroup unitGroup = unitGroupBDConvertor.btod(bProduct.getUnitGroup(), true);
            product.setUnitGroup(unitGroup);
        } else {
            product.setUnitGroup(null);
        }

        // unit
        if (bProduct.getUnit() != null) {
            UnitBDConvertor unitBDConvertor = new UnitBDConvertor();
            Unit unit = (Unit) unitBDConvertor.btod(bProduct.getUnit());
            product.setUnit(unit);
        } else {
            product.setUnit(null);
        }
    }

    /*
     * (non-Javadoc) 基本属性,处理; 引用的对象属性unitGroup、productLayer,处理;
     * 引用的对象属性parentProduct,不处理; 下附的集合属性productUnits、productProCharacters,不处理;
     * 下附的集合属性subProducts,不处理
     * 
     * @see
     * dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang
     * .Object)
     */
    public Object btod(Object b_obj) {
        if (b_obj == null) {
            return null;
        }
        Product product = new Product();
        this.btod(b_obj, product);
        return product;
    }

    /*
     * (non-Javadoc) 基本属性,处理; 引用的对象属性unitGroup、productLayer,处理;
     * 引用的对象属性parentProduct,不处理; 下附的集合属性productUnits、productProCharacters,不处理;
     * 下附的集合属性subProducts,不处理
     * 
     * @see
     * dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang
     * .Object, java.lang.Object)
     */
    public void dtob(Object d_obj, Object b_obj) {
        Product product = null;
        BProduct bProduct = null;

        if (d_obj == null) {
            product = new Product();
        } else {
            product = (Product) d_obj;
        }

        if (b_obj == null) {
            return;
        } else {
            bProduct = (BProduct) b_obj;
        }

        bProduct.setVersion(product.getVersion());
        bProduct.setId(product.getId());
        bProduct.setCode(product.getCode());
        bProduct.setName(product.getName());
        bProduct.setIsCatalog(product.getIsCatalog());
        bProduct.setIsValid(product.getIsValid());
        bProduct.setDescription(product.getDescription());
        bProduct.setComments(product.getComments());
        bProduct.setPathCode(product.getPathCode());
        // 新增过期时间、 是否套装、 采购提前期、 下架提前期
        bProduct.setShelfLife(product.getShelfLife());
        bProduct.setIsSuit(product.getIsSuit());
        bProduct.setPurchaseLeadTime(product.getPurchaseLeadTime());
        bProduct.setWithdrawLeadTime(product.getWithdrawLeadTime());

        // productLayer
        if (product.getProductLayer() != null) {
            ProductLayerBDConvertor productLayerBDConvertor = new ProductLayerBDConvertor();
            BProductLayer bProductLayer = (BProductLayer) productLayerBDConvertor.dtob(product.getProductLayer());
            bProduct.setProductLayer(bProductLayer);
        } else {
            bProduct.setProductLayer(null);
        }
        // unitGroup
        if (product.getUnitGroup() != null) {
            UnitGroupBDConvertor unitGroupBDConvertor = new UnitGroupBDConvertor();
            BUnitGroup bUnitGroup = unitGroupBDConvertor.dtob(product.getUnitGroup(), true);
            bProduct.setUnitGroup(bUnitGroup);
        } else {
            bProduct.setUnitGroup(null);
        }

        // unit
        if (product.getUnit() != null) {
            UnitBDConvertor unitBDConvertor = new UnitBDConvertor();
            BUnit bUnit = (BUnit) unitBDConvertor.dtob(product.getUnit());
            bProduct.setUnit(bUnit);
        } else {
            bProduct.setUnit(null);
        }

    }

    /*
     * (non-Javadoc) 基本属性,处理; 引用的对象属性unitGroup、productLayer,处理;
     * 引用的对象属性parentProduct,不处理; 下附的集合属性productUnits、productProCharacters,不处理;
     * 下附的集合属性subProducts,不处理
     * 
     * @see
     * dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang
     * .Object)
     */
    public Object dtob(Object d_obj) {
        if (d_obj == null) {
            return null;
        }
        BProduct bProduct = new BProduct();
        this.dtob(d_obj, bProduct);
        return bProduct;
    }

    public void btod(BProduct _bProduct, Product _product, boolean _blWithProductProCharacters) {
        if (_product == null) {
            return;
        }

        this.btod(_bProduct, _product);

        if (_blWithProductProCharacters == true) {
            // productProCharacters
            if (_bProduct != null && _bProduct.getProductProCharacters() != null && _bProduct.getProductProCharacters().iterator() != null) {
                ProductCharacterBDConvertor productCharacterBDConvertor = new ProductCharacterBDConvertor();

                Iterator<BProductProCharacter> itr_bProductProCharacters = _bProduct.getProductProCharacters().iterator();
                while (itr_bProductProCharacters.hasNext()) {
                    BProductProCharacter bProductProCharacter = itr_bProductProCharacters.next();

                    ProductCharacter productCharacter = (ProductCharacter) productCharacterBDConvertor.btod(bProductProCharacter.getProductCharacter()); // 认为bProductProCharacter.getProductCharacter()!=null

                    ProductProCharacter productProCharacter = new ProductProCharacter();

                    productProCharacter.setVersion(bProductProCharacter.getVersion());
                    productProCharacter.setId(bProductProCharacter.getId());
                    productProCharacter.setProduct(_product);
                    productProCharacter.setProductCharacter(productCharacter);

                    _product.addProductProCharacter(productProCharacter);
                }
            }
        }

    }

    public Product btod(BProduct _bProduct, boolean _blWithProductProCharacters) {
        if (_bProduct == null) {
            return null;
        }
        Product product = new Product();
        this.btod(_bProduct, product, _blWithProductProCharacters);
        return product;
    }

    public void dtob(Product _product, BProduct _bProduct, boolean _blWithProductProCharacters) {
        if (_bProduct == null) {
            return;
        }

        this.dtob(_product, _bProduct);

        if (_blWithProductProCharacters == true) {
            // productProCharacters
            if (_product != null && _product.getProductProCharacters() != null && _product.getProductProCharacters().iterator() != null) {
                // 直接从内存常驻变量中获取，获取完整树上的节点，2011.01.21 by liuzhen begin
                /*
                 * ProductCharacterBDConvertor productCharacterBDConvertor = new
                 * ProductCharacterBDConvertor();
                 */
                // 直接从内存常驻变量中获取，获取完整树上的节点，2011.01.21 by liuzhen end

                Iterator<ProductProCharacter> itr_productProCharacters = _product.getProductProCharacters().iterator();
                while (itr_productProCharacters.hasNext()) {
                    ProductProCharacter productProCharacter = itr_productProCharacters.next();

                    // 直接从内存常驻变量中获取，获取完整树上的节点，2011.01.21 by liuzhen begin
                    /*
                     * BProductCharacter bProductCharacter =
                     * (BProductCharacter)productCharacterBDConvertor
                     * .dtob(productProCharacter.getProductCharacter()); //
                     * 认为productProCharacter.getProductCharacter()!=null
                     */
                    BProductCharacter bProductCharacter = ServerEnvironment.getInstance().getBProductCharacter(
                            productProCharacter.getProductCharacter().getId());
                    // 直接从内存常驻变量中获取，获取完整树上的节点，2011.01.21 by liuzhen end

                    BProductProCharacter bProductProCharacter = new BProductProCharacter();

                    bProductProCharacter.setVersion(productProCharacter.getVersion());
                    bProductProCharacter.setId(productProCharacter.getId());
                    bProductProCharacter.setProduct(_bProduct);
                    bProductProCharacter.setProductCharacter(bProductCharacter);

                    _bProduct.addProductProCharacters(bProductProCharacter);
                }
            }
        }

    }

    public BProduct dtob(Product _product, boolean _blWithProductProCharacters) {
        if (_product == null) {
            return null;
        }
        BProduct bProduct = new BProduct();
        this.dtob(_product, bProduct, _blWithProductProCharacters);
        return bProduct;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
