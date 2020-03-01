package dmdd.dmddjava.dataaccess.bizobject;

import java.io.Serializable;

import dmdd.dmddjava.common.constant.BizConst;


/** @author Hibernate CodeGenerator */
public class BBizDataDefItemFcComb extends BBizDataDefItem implements Serializable {
	
	public final static long serialVersionUID = -1020000003;

    /** persistent field */
    private Double coefficient;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.bizobject.BBizData itemBizData;

    /** full constructor */
    public BBizDataDefItemFcComb(String indicator, Long version, dmdd.dmddjava.dataaccess.bizobject.BBizData bizData, Double coefficient, dmdd.dmddjava.dataaccess.bizobject.BBizData itemBizData) {
        super(indicator, version, bizData);
        this.coefficient = coefficient;
        this.itemBizData = itemBizData;
    }

    /** default constructor */
    public BBizDataDefItemFcComb() {
    	this.setIndicator( BizConst.BIZDATADEFITEM_INDICATOR_FCCOMB );
    }

    /** minimal constructor */
    public BBizDataDefItemFcComb(String indicator, dmdd.dmddjava.dataaccess.bizobject.BBizData bizData, Double coefficient) {
      super(indicator, bizData);
        this.coefficient = coefficient;
    }

    public Double getCoefficient() {
        return this.coefficient;
    }

    public void setCoefficient(Double coefficient) {
        this.coefficient = coefficient;
    }

    public dmdd.dmddjava.dataaccess.bizobject.BBizData getItemBizData() {
        return this.itemBizData;
    }

    public void setItemBizData(dmdd.dmddjava.dataaccess.bizobject.BBizData itemBizData) {
        this.itemBizData = itemBizData;
    }

    public String toString() {
        return super.toString();
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((itemBizData == null) ? 0 : itemBizData.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj )
	{
		if( this == obj )
			return true;
		if( !super.equals( obj ) )
			return false;
		if( getClass() != obj.getClass() )
			return false;
		BBizDataDefItemFcComb other = (BBizDataDefItemFcComb) obj;
		if( itemBizData == null )
		{
			if( other.itemBizData != null )
				return false;
		}
		else if( !itemBizData.equals( other.itemBizData ) )
			return false;
		return true;
	}

}
