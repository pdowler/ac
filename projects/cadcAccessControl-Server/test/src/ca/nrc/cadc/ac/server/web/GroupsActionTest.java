/*
 ************************************************************************
 *******************  CANADIAN ASTRONOMY DATA CENTRE  *******************
 **************  CENTRE CANADIEN DE DONNÉES ASTRONOMIQUES  **************
 *
 *  (c) 2014.                            (c) 2014.
 *  Government of Canada                 Gouvernement du Canada
 *  National Research Council            Conseil national de recherches
 *  Ottawa, Canada, K1A 0R6              Ottawa, Canada, K1A 0R6
 *  All rights reserved                  Tous droits réservés
 *
 *  NRC disclaims any warranties,        Le CNRC dénie toute garantie
 *  expressed, implied, or               énoncée, implicite ou légale,
 *  statutory, of any kind with          de quelque nature que ce
 *  respect to the software,             soit, concernant le logiciel,
 *  including without limitation         y compris sans restriction
 *  any warranty of merchantability      toute garantie de valeur
 *  or fitness for a particular          marchande ou de pertinence
 *  purpose. NRC shall not be            pour un usage particulier.
 *  liable in any event for any          Le CNRC ne pourra en aucun cas
 *  damages, whether direct or           être tenu responsable de tout
 *  indirect, special or general,        dommage, direct ou indirect,
 *  consequential or incidental,         particulier ou général,
 *  arising from the use of the          accessoire ou fortuit, résultant
 *  software.  Neither the name          de l'utilisation du logiciel. Ni
 *  of the National Research             le nom du Conseil National de
 *  Council of Canada nor the            Recherches du Canada ni les noms
 *  names of its contributors may        de ses  participants ne peuvent
 *  be used to endorse or promote        être utilisés pour approuver ou
 *  products derived from this           promouvoir les produits dérivés
 *  software without specific prior      de ce logiciel sans autorisation
 *  written permission.                  préalable et particulière
 *                                       par écrit.
 *
 *  This file is part of the             Ce fichier fait partie du projet
 *  OpenCADC project.                    OpenCADC.
 *
 *  OpenCADC is free software:           OpenCADC est un logiciel libre ;
 *  you can redistribute it and/or       vous pouvez le redistribuer ou le
 *  modify it under the terms of         modifier suivant les termes de
 *  the GNU Affero General Public        la “GNU Affero General Public
 *  License as published by the          License” telle que publiée
 *  Free Software Foundation,            par la Free Software Foundation
 *  either version 3 of the              : soit la version 3 de cette
 *  License, or (at your option)         licence, soit (à votre gré)
 *  any later version.                   toute version ultérieure.
 *
 *  OpenCADC is distributed in the       OpenCADC est distribué
 *  hope that it will be useful,         dans l’espoir qu’il vous
 *  but WITHOUT ANY WARRANTY;            sera utile, mais SANS AUCUNE
 *  without even the implied             GARANTIE : sans même la garantie
 *  warranty of MERCHANTABILITY          implicite de COMMERCIALISABILITÉ
 *  or FITNESS FOR A PARTICULAR          ni d’ADÉQUATION À UN OBJECTIF
 *  PURPOSE.  See the GNU Affero         PARTICULIER. Consultez la Licence
 *  General Public License for           Générale Publique GNU Affero
 *  more details.                        pour plus de détails.
 *
 *  You should have received             Vous devriez avoir reçu une
 *  a copy of the GNU Affero             copie de la Licence Générale
 *  General Public License along         Publique GNU Affero avec
 *  with OpenCADC.  If not, see          OpenCADC ; si ce n’est
 *  <http://www.gnu.org/licenses/>.      pas le cas, consultez :
 *                                       <http://www.gnu.org/licenses/>.
 *
 *  $Revision: 4 $
 *
 ************************************************************************
 */
package ca.nrc.cadc.ac.server.web;

import ca.nrc.cadc.ac.GroupAlreadyExistsException;
import ca.nrc.cadc.ac.GroupNotFoundException;
import ca.nrc.cadc.ac.MemberAlreadyExistsException;
import ca.nrc.cadc.ac.MemberNotFoundException;
import ca.nrc.cadc.ac.UserNotFoundException;
import ca.nrc.cadc.net.TransientException;
import ca.nrc.cadc.util.Log4jInit;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.AccessControlException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.easymock.EasyMock;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jburke
 */
public class GroupsActionTest
{
    private final static Logger log = Logger.getLogger(GroupsActionTest.class);
    
    @BeforeClass
    public static void setUpClass()
    {
        Log4jInit.setLevel("ca.nrc.cadc.ac", Level.INFO);
    }

    @Test
    @Ignore
    public void testDoActionAccessControlException() throws Exception
    {
        String message = "Permission Denied";
        int responseCode = 403;
        Exception e = new AccessControlException("");
        testDoAction(message, responseCode, e);
    }
    
    @Test
    @Ignore
    public void testDoActionIllegalArgumentException() throws Exception
    {
        String message = "message";
        int responseCode = 400;
        Exception e = new IllegalArgumentException("message");
        testDoAction(message, responseCode, e);
    }
    
    @Test
    @Ignore
    public void testDoActionMemberNotFoundException() throws Exception
    {
        String message = "Member not found: foo";
        int responseCode = 404;
        Exception e = new MemberNotFoundException("foo");
        testDoAction(message, responseCode, e);
    }
    
    @Test
    @Ignore
    public void testDoActionGroupNotFoundException() throws Exception
    {
        String message = "Group not found: foo";
        int responseCode = 404;
        Exception e = new GroupNotFoundException("foo");
        testDoAction(message, responseCode, e);
    }
    
    @Test
    @Ignore
    public void testDoActionUserNotFoundException() throws Exception
    {
        String message = "User not found: foo";
        int responseCode = 404;
        Exception e = new UserNotFoundException("foo");
        testDoAction(message, responseCode, e);
    }

    @Test
    @Ignore
    public void testDoActionMemberAlreadyExistsException() throws Exception
    {
        String message = "Member already exists: foo";
        int responseCode = 409;
        Exception e = new MemberAlreadyExistsException("foo");
        testDoAction(message, responseCode, e);
    }
    
    @Test
    @Ignore
    public void testDoActionGroupAlreadyExistsException() throws Exception
    {
        String message = "Group already exists: foo";
        int responseCode = 409;
        Exception e = new GroupAlreadyExistsException("foo");
        testDoAction(message, responseCode, e);
    }
    
    @Test
    @Ignore
    public void testDoActionUnsupportedOperationException() throws Exception
    {
        String message = "Not yet implemented.";
        int responseCode = 501;
        Exception e = new UnsupportedOperationException();
        testDoAction(message, responseCode, e);
    }
    
    @Test
    @Ignore
    public void testDoActionTransientException() throws Exception
    {
        try
        {
            ServletOutputStream out = EasyMock.createMock(ServletOutputStream.class);

            HttpServletResponse response = EasyMock.createMock(HttpServletResponse.class);
            EasyMock.expect(response.isCommitted()).andReturn(Boolean.FALSE);
            response.setHeader("Content-Type", "text/plain");
            EasyMock.expectLastCall().once();
            EasyMock.expect(response.getOutputStream()).andReturn(out);
            EasyMock.expect(response.getWriter()).andReturn(new PrintWriter(new StringWriter()));
            EasyMock.expectLastCall().once();
            response.setStatus(503);
            EasyMock.expectLastCall().once();
            
            GroupLogInfo logInfo = EasyMock.createMock(GroupLogInfo.class);
            logInfo.setSuccess(false);
            EasyMock.expectLastCall().once();
            logInfo.setMessage("Internal Transient Error: foo");
            EasyMock.expectLastCall().once();

            EasyMock.replay(out, response, logInfo);
            
            GroupsActionImpl action = new GroupsActionImpl(logInfo);
            action.setException(new TransientException("foo"));
            action.doAction(null, response);
        }
        catch (Throwable t)
        {
            log.error(t.getMessage(), t);
            fail("unexpected error: " + t.getMessage());
        }
    }
    
    private void testDoAction(final String message, final int responseCode, final Exception e)
        throws Exception
    {
        try
        {
            ServletOutputStream out = EasyMock.createMock(ServletOutputStream.class);
            out.write(message.getBytes());
            EasyMock.expectLastCall().once();

            HttpServletResponse response = EasyMock.createMock(HttpServletResponse.class);
            response.setHeader("Content-Type", "text/plain");
            EasyMock.expectLastCall().once();
            response.setStatus(responseCode);
            EasyMock.expectLastCall().once();
            EasyMock.expect(response.getOutputStream()).andReturn(out);

            GroupLogInfo logInfo = EasyMock.createMock(GroupLogInfo.class);
            logInfo.setMessage(message);
            EasyMock.expectLastCall().once();

            EasyMock.replay(out, response, logInfo);
            
            GroupsActionImpl action = new GroupsActionImpl(logInfo);
            action.setException(e);
            action.doAction(null, response);
        }
        catch (Throwable t)
        {
            log.error(t.getMessage(), t);
            fail("unexpected error: " + t.getMessage());
        }
    }

    public class GroupsActionImpl extends GroupsAction
    {
        Exception exception;
        
        public GroupsActionImpl(GroupLogInfo logInfo)
        {
            super(logInfo);
        }

        public Object run() throws Exception
        {
            throw exception;
        }

        public void setException(Exception e)
        {
            this.exception = e;
        }
    }
    
}
