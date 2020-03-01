/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BSysParam;
import dmdd.dmddjava.dataaccess.dataobject.SysParam;

/**
 * @author liuzhen
 *
 */
public class SysParamBDConvertor implements BDConvertorInterface
{

	/**
	 * 
	 */
	public SysParamBDConvertor()
	{
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object, java.lang.Object)
	 */
	public void btod( Object b_obj, Object d_obj )
	{
		BSysParam bSysParam = null;
		SysParam   sysParam = null;
		if( b_obj == null )
		{
			bSysParam = new BSysParam();
		}
		else
		{
			bSysParam = (BSysParam) b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			sysParam = (SysParam) d_obj;
		}
		
		sysParam.setVersion( bSysParam.getVersion() );
		sysParam.setId( bSysParam.getId() );
		sysParam.setCode( bSysParam.getCode() );
		sysParam.setValue( bSysParam.getValue() );
		sysParam.setValueType( bSysParam.getValueType() );
		sysParam.setDescription( bSysParam.getDescription() );
		sysParam.setComments( bSysParam.getComments() );
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object)
	 */
	public Object btod( Object b_obj )
	{
		SysParam sysParam = new SysParam();
		this.btod(b_obj, sysParam);
		return sysParam;
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object, java.lang.Object)
	 */
	public void dtob( Object d_obj, Object b_obj )
	{
		SysParam   sysParam = null;
		BSysParam bSysParam = null;
		
		if( d_obj == null )
		{
			sysParam = new SysParam();
		}
		else
		{
			sysParam = (SysParam) d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bSysParam = (BSysParam) b_obj;
		}
		
		bSysParam.setVersion( sysParam.getVersion() );
		bSysParam.setId( sysParam.getId() );
		bSysParam.setCode( sysParam.getCode() );
		bSysParam.setValue( sysParam.getValue() );
		bSysParam.setValueType( sysParam.getValueType() );
		bSysParam.setDescription( sysParam.getDescription() );
		bSysParam.setComments( sysParam.getComments() );

	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object)
	 */
	public Object dtob( Object d_obj )
	{
		BSysParam bSysParam = new BSysParam();
		this.dtob(d_obj, bSysParam);
		return bSysParam;
	}

	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
		// TODO Auto-generated method stub

	}

}
