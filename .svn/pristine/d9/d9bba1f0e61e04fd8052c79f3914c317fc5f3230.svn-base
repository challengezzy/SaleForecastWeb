/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BFunPermission;
import dmdd.dmddjava.dataaccess.dataobject.FunPermission;

/**
 * @author liuzhen
 *
 */
public class FunPermissionBDConvertor implements BDConvertorInterface
{

	/**
	 * 
	 */
	public FunPermissionBDConvertor()
	{
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object, java.lang.Object)
	 */
	public void btod(Object b_obj, Object d_obj)
	{
		BFunPermission bFunPermission = null;
		FunPermission   funPermission = null;
		if( b_obj == null )
		{
			bFunPermission = new BFunPermission();
		}
		else
		{
			bFunPermission = (BFunPermission) b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			funPermission = (FunPermission) d_obj;
		}
		
		funPermission.setVersion( bFunPermission.getVersion() );
		funPermission.setId( bFunPermission.getId() );
		funPermission.setCode( bFunPermission.getCode() );
		funPermission.setName( bFunPermission.getName() );
		funPermission.setDescription( bFunPermission.getDescription() );
		funPermission.setComments( bFunPermission.getComments() );
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object)
	 */
	public Object btod(Object b_obj)
	{
		FunPermission funPermission = new FunPermission();
		this.btod(b_obj, funPermission);
		return funPermission;
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object, java.lang.Object)
	 */
	public void dtob(Object d_obj, Object b_obj)
	{
		FunPermission   funPermission = null;
		BFunPermission bFunPermission = null;
		
		if( d_obj == null )
		{
			funPermission = new FunPermission();
		}
		else
		{
			funPermission = (FunPermission) d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bFunPermission = (BFunPermission) b_obj;
		}
		
		bFunPermission.setVersion( funPermission.getVersion() );
		bFunPermission.setId( funPermission.getId() );
		bFunPermission.setCode( funPermission.getCode() );
		bFunPermission.setName( funPermission.getName() );
		bFunPermission.setDescription( funPermission.getDescription() );
		bFunPermission.setComments( funPermission.getComments() );
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object)
	 */
	public Object dtob(Object d_obj)
	{
		BFunPermission bFunPermission = new BFunPermission();
		this.dtob(d_obj, bFunPermission);
		return bFunPermission;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
