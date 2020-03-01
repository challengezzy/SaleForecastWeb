/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import java.util.Iterator;

import dmdd.dmddjava.dataaccess.bizobject.BBulletin;
import dmdd.dmddjava.dataaccess.bizobject.BBulletinOperatorUser;
import dmdd.dmddjava.dataaccess.bizobject.BOperatorUser;
import dmdd.dmddjava.dataaccess.dataobject.Bulletin;
import dmdd.dmddjava.dataaccess.dataobject.BulletinOperatorUser;
import dmdd.dmddjava.dataaccess.dataobject.OperatorUser;

/**
 * @author liuzhen
 *
 */
public class BulletinBDConvertor implements BDConvertorInterface
{

	/**
	 * 
	 */
	public BulletinBDConvertor()
	{
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object, java.lang.Object)
	 */
	public void btod( Object b_obj, Object d_obj )
	{
		BBulletin bBulletin = null;
		Bulletin   bulletin = null;
		
		if( b_obj == null )
		{
			bBulletin = new BBulletin();
		}
		else
		{
			bBulletin = (BBulletin)b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			bulletin = (Bulletin)d_obj;
		}
		
		bulletin.setVersion( bBulletin.getVersion() );
		bulletin.setId( bBulletin.getId() );
		bulletin.setTitle( bBulletin.getTitle() );
		bulletin.setSummary( bBulletin.getSummary() );
		bulletin.setContent( bBulletin.getContent() );
		bulletin.setCreateTime( bBulletin.getCreateTime() );		
		bulletin.setModifyTime( bBulletin.getModifyTime() );
		bulletin.setPublishTime( bBulletin.getPublishTime() );
		bulletin.setIsPublish( bBulletin.getIsPublish() );
		bulletin.setComments( bBulletin.getComments() );
		
		//    operatorUser
		if( bBulletin.getOperatorUser() != null )
		{
			OperatorUserBDConvertor operatorUserBDConvertor = new OperatorUserBDConvertor();
			OperatorUser operatorUser = (OperatorUser) operatorUserBDConvertor.btod( bBulletin.getOperatorUser() );	
			bulletin.setOperatorUser(operatorUser);
		}
		else
		{
			bulletin.setOperatorUser(null);
		}	

	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object)
	 */
	public Object btod( Object b_obj )
	{
		if( b_obj == null )
		{
			return null;
		}
		Bulletin bulletin = new Bulletin();
		this.btod(b_obj, bulletin);
		return bulletin;
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object, java.lang.Object)
	 */
	public void dtob( Object d_obj, Object b_obj )
	{
		Bulletin   bulletin = null;
		BBulletin bBulletin = null;
		
		if( d_obj == null )
		{
			bulletin = new Bulletin();
		}
		else
		{
			bulletin = (Bulletin)d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bBulletin = (BBulletin)b_obj;
		}
		
		bBulletin.setVersion( bulletin.getVersion() );
		bBulletin.setId( bulletin.getId() );
		bBulletin.setTitle( bulletin.getTitle() );
		bBulletin.setSummary( bulletin.getSummary() );
		bBulletin.setContent( bulletin.getContent() );
		bBulletin.setCreateTime( bulletin.getCreateTime() );		
		bBulletin.setModifyTime( bulletin.getModifyTime() );
		bBulletin.setPublishTime( bulletin.getPublishTime() );
		bBulletin.setIsPublish( bulletin.getIsPublish() );
		bBulletin.setComments( bulletin.getComments() );
		

		//    operatorUser
		if( bulletin.getOperatorUser() != null )
		{
			OperatorUserBDConvertor operatorUserBDConvertor = new OperatorUserBDConvertor();
			BOperatorUser bOperatorUser = (BOperatorUser) operatorUserBDConvertor.dtob( bulletin.getOperatorUser() );	
			bBulletin.setOperatorUser(bOperatorUser);
		}
		else
		{
			bBulletin.setOperatorUser(null);
		}	

	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object)
	 */
	public Object dtob( Object d_obj )
	{
		if( d_obj == null )
		{
			return null;
		}		
		BBulletin bBulletin = new BBulletin();
		this.dtob(d_obj, bBulletin);
		return bBulletin;
	}
	
	
	
	
	public void btod( BBulletin _bBulletin, Bulletin _bulletin, boolean _blWithBulletinOperatorUsers )
	{
		if( _bulletin == null )
		{
			return;
		}
		
		this.btod( _bBulletin, _bulletin );
		
		if( _blWithBulletinOperatorUsers == true )
		{
			// bulletinOperatorUsers
			if( _bBulletin.getBulletinOperatorUsers() != null && !(_bBulletin.getBulletinOperatorUsers().isEmpty()) )
			{
				OperatorUserBDConvertor operatorUserBDConvertor = new OperatorUserBDConvertor();
				
				Iterator<BBulletinOperatorUser> itr_bBulletinOperatorUsers = _bBulletin.getBulletinOperatorUsers().iterator();
				while( itr_bBulletinOperatorUsers.hasNext() )
				{
					BBulletinOperatorUser bBulletinOperatorUser = itr_bBulletinOperatorUsers.next();
					
					BulletinOperatorUser bulletinOperatorUser = new BulletinOperatorUser();
					
					bulletinOperatorUser.setVersion(bBulletinOperatorUser.getVersion());
					bulletinOperatorUser.setId(bBulletinOperatorUser.getId());
					bulletinOperatorUser.setOperatorUser( (OperatorUser)operatorUserBDConvertor.btod( bBulletinOperatorUser.getOperatorUser() ) );
					bulletinOperatorUser.setBulletin( _bulletin );
					
					_bulletin.addBulletinOperatorUser( bulletinOperatorUser );
					
				}
			}
		}
	}	
	
	public Bulletin btod( BBulletin _bBulletin, boolean _blWithBulletinOperatorUsers )
	{
		if( _bBulletin == null )
		{
			return null;
		}
		
		Bulletin bulletin = new Bulletin();
		this.btod( _bBulletin, bulletin, _blWithBulletinOperatorUsers );
		
		return bulletin;
	}
	
	public void dtob( Bulletin _bulletin, BBulletin _bBulletin, boolean _blWithBulletinOperatorUsers )
	{
		if( _bBulletin == null )
		{
			return;
		}
		
		this.dtob( _bulletin, _bBulletin );
		
		if( _blWithBulletinOperatorUsers == true )
		{
			// bulletinOperatorUsers
			if( _bulletin.getBulletinOperatorUsers() != null && !(_bulletin.getBulletinOperatorUsers().isEmpty()) )
			{
				OperatorUserBDConvertor operatorUserBDConvertor = new OperatorUserBDConvertor();
				
				Iterator<BulletinOperatorUser> itr_bulletinOperatorUsers = _bulletin.getBulletinOperatorUsers().iterator();
				while( itr_bulletinOperatorUsers.hasNext() )
				{
					BulletinOperatorUser bulletinOperatorUser = itr_bulletinOperatorUsers.next();
					
					BBulletinOperatorUser bBulletinOperatorUser = new BBulletinOperatorUser();
					
					bBulletinOperatorUser.setVersion(bulletinOperatorUser.getVersion());
					bBulletinOperatorUser.setId(bulletinOperatorUser.getId());
					bBulletinOperatorUser.setOperatorUser( (BOperatorUser)operatorUserBDConvertor.dtob( bulletinOperatorUser.getOperatorUser() ) );
					bBulletinOperatorUser.setBulletin( _bBulletin );
					
					_bBulletin.addBulletinOperatorUser( bBulletinOperatorUser );
				}
			}
		}
	}	
	
	public BBulletin dtob( Bulletin _bulletin, boolean _blWithBulletinOperatorUsers )
	{
		if( _bulletin == null )
		{
			return null;
		}
		
		BBulletin bBulletin = new BBulletin();
		this.dtob( _bulletin, bBulletin, _blWithBulletinOperatorUsers );
		
		return bBulletin;
	}	
	

	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
		// TODO Auto-generated method stub

	}

}
