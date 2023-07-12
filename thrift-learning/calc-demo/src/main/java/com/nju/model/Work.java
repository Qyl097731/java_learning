package com.nju.model; /**
 * Autogenerated by Thrift Compiler (0.8.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;

import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;

/**
 * optional可以不设置值，没有值就不会序列化
 */
public class Work implements org.apache.thrift.TBase<Work, Work._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Work");

  private static final org.apache.thrift.protocol.TField NUM1_FIELD_DESC = new org.apache.thrift.protocol.TField("num1", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField NUM2_FIELD_DESC = new org.apache.thrift.protocol.TField("num2", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField OP_FIELD_DESC = new org.apache.thrift.protocol.TField("op", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField COMMIT_FIELD_DESC = new org.apache.thrift.protocol.TField("commit", org.apache.thrift.protocol.TType.STRING, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new WorkStandardSchemeFactory());
    schemes.put(TupleScheme.class, new WorkTupleSchemeFactory());
  }

  public int num1; // required
  public int num2; // required
  /**
   * 
   * @see Operation
   */
  public Operation op; // required
  public String commit; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    NUM1((short)1, "num1"),
    NUM2((short)2, "num2"),
    /**
     * 
     * @see Operation
     */
    OP((short)3, "op"),
    COMMIT((short)4, "commit");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // NUM1
          return NUM1;
        case 2: // NUM2
          return NUM2;
        case 3: // OP
          return OP;
        case 4: // COMMIT
          return COMMIT;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __NUM1_ISSET_ID = 0;
  private static final int __NUM2_ISSET_ID = 1;
  private BitSet __isset_bit_vector = new BitSet(2);
  private _Fields optionals[] = {_Fields.COMMIT};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.NUM1, new org.apache.thrift.meta_data.FieldMetaData("num1", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.NUM2, new org.apache.thrift.meta_data.FieldMetaData("num2", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.OP, new org.apache.thrift.meta_data.FieldMetaData("op", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, Operation.class)));
    tmpMap.put(_Fields.COMMIT, new org.apache.thrift.meta_data.FieldMetaData("commit", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Work.class, metaDataMap);
  }

  public Work() {
    this.num1 = 0;

  }

  public Work(
    int num1,
    int num2,
    Operation op)
  {
    this();
    this.num1 = num1;
    setNum1IsSet(true);
    this.num2 = num2;
    setNum2IsSet(true);
    this.op = op;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Work(Work other) {
    __isset_bit_vector.clear();
    __isset_bit_vector.or(other.__isset_bit_vector);
    this.num1 = other.num1;
    this.num2 = other.num2;
    if (other.isSetOp()) {
      this.op = other.op;
    }
    if (other.isSetCommit()) {
      this.commit = other.commit;
    }
  }

  public Work deepCopy() {
    return new Work(this);
  }

  @Override
  public void clear() {
    this.num1 = 0;

    setNum2IsSet(false);
    this.num2 = 0;
    this.op = null;
    this.commit = null;
  }

  public int getNum1() {
    return this.num1;
  }

  public Work setNum1(int num1) {
    this.num1 = num1;
    setNum1IsSet(true);
    return this;
  }

  public void unsetNum1() {
    __isset_bit_vector.clear(__NUM1_ISSET_ID);
  }

  /** Returns true if field num1 is set (has been assigned a value) and false otherwise */
  public boolean isSetNum1() {
    return __isset_bit_vector.get(__NUM1_ISSET_ID);
  }

  public void setNum1IsSet(boolean value) {
    __isset_bit_vector.set(__NUM1_ISSET_ID, value);
  }

  public int getNum2() {
    return this.num2;
  }

  public Work setNum2(int num2) {
    this.num2 = num2;
    setNum2IsSet(true);
    return this;
  }

  public void unsetNum2() {
    __isset_bit_vector.clear(__NUM2_ISSET_ID);
  }

  /** Returns true if field num2 is set (has been assigned a value) and false otherwise */
  public boolean isSetNum2() {
    return __isset_bit_vector.get(__NUM2_ISSET_ID);
  }

  public void setNum2IsSet(boolean value) {
    __isset_bit_vector.set(__NUM2_ISSET_ID, value);
  }

  /**
   * 
   * @see Operation
   */
  public Operation getOp() {
    return this.op;
  }

  /**
   * 
   * @see Operation
   */
  public Work setOp(Operation op) {
    this.op = op;
    return this;
  }

  public void unsetOp() {
    this.op = null;
  }

  /** Returns true if field op is set (has been assigned a value) and false otherwise */
  public boolean isSetOp() {
    return this.op != null;
  }

  public void setOpIsSet(boolean value) {
    if (!value) {
      this.op = null;
    }
  }

  public String getCommit() {
    return this.commit;
  }

  public Work setCommit(String commit) {
    this.commit = commit;
    return this;
  }

  public void unsetCommit() {
    this.commit = null;
  }

  /** Returns true if field commit is set (has been assigned a value) and false otherwise */
  public boolean isSetCommit() {
    return this.commit != null;
  }

  public void setCommitIsSet(boolean value) {
    if (!value) {
      this.commit = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case NUM1:
      if (value == null) {
        unsetNum1();
      } else {
        setNum1((Integer)value);
      }
      break;

    case NUM2:
      if (value == null) {
        unsetNum2();
      } else {
        setNum2((Integer)value);
      }
      break;

    case OP:
      if (value == null) {
        unsetOp();
      } else {
        setOp((Operation)value);
      }
      break;

    case COMMIT:
      if (value == null) {
        unsetCommit();
      } else {
        setCommit((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case NUM1:
      return Integer.valueOf(getNum1());

    case NUM2:
      return Integer.valueOf(getNum2());

    case OP:
      return getOp();

    case COMMIT:
      return getCommit();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case NUM1:
      return isSetNum1();
    case NUM2:
      return isSetNum2();
    case OP:
      return isSetOp();
    case COMMIT:
      return isSetCommit();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Work)
      return this.equals((Work)that);
    return false;
  }

  public boolean equals(Work that) {
    if (that == null)
      return false;

    boolean this_present_num1 = true;
    boolean that_present_num1 = true;
    if (this_present_num1 || that_present_num1) {
      if (!(this_present_num1 && that_present_num1))
        return false;
      if (this.num1 != that.num1)
        return false;
    }

    boolean this_present_num2 = true;
    boolean that_present_num2 = true;
    if (this_present_num2 || that_present_num2) {
      if (!(this_present_num2 && that_present_num2))
        return false;
      if (this.num2 != that.num2)
        return false;
    }

    boolean this_present_op = true && this.isSetOp();
    boolean that_present_op = true && that.isSetOp();
    if (this_present_op || that_present_op) {
      if (!(this_present_op && that_present_op))
        return false;
      if (!this.op.equals(that.op))
        return false;
    }

    boolean this_present_commit = true && this.isSetCommit();
    boolean that_present_commit = true && that.isSetCommit();
    if (this_present_commit || that_present_commit) {
      if (!(this_present_commit && that_present_commit))
        return false;
      if (!this.commit.equals(that.commit))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(Work other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    Work typedOther = (Work)other;

    lastComparison = Boolean.valueOf(isSetNum1()).compareTo(typedOther.isSetNum1());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetNum1()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.num1, typedOther.num1);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetNum2()).compareTo(typedOther.isSetNum2());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetNum2()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.num2, typedOther.num2);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetOp()).compareTo(typedOther.isSetOp());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOp()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.op, typedOther.op);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCommit()).compareTo(typedOther.isSetCommit());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCommit()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.commit, typedOther.commit);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Work(");
    boolean first = true;

    sb.append("num1:");
    sb.append(this.num1);
    first = false;
    if (!first) sb.append(", ");
    sb.append("num2:");
    sb.append(this.num2);
    first = false;
    if (!first) sb.append(", ");
    sb.append("op:");
    if (this.op == null) {
      sb.append("null");
    } else {
      sb.append(this.op);
    }
    first = false;
    if (isSetCommit()) {
      if (!first) sb.append(", ");
      sb.append("commit:");
      if (this.commit == null) {
        sb.append("null");
      } else {
        sb.append(this.commit);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bit_vector = new BitSet(1);
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class WorkStandardSchemeFactory implements SchemeFactory {
    public WorkStandardScheme getScheme() {
      return new WorkStandardScheme();
    }
  }

  private static class WorkStandardScheme extends StandardScheme<Work> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Work struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // NUM1
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.num1 = iprot.readI32();
              struct.setNum1IsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // NUM2
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.num2 = iprot.readI32();
              struct.setNum2IsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // OP
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.op = Operation.findByValue(iprot.readI32());
              struct.setOpIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // COMMIT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.commit = iprot.readString();
              struct.setCommitIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, Work struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(NUM1_FIELD_DESC);
      oprot.writeI32(struct.num1);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(NUM2_FIELD_DESC);
      oprot.writeI32(struct.num2);
      oprot.writeFieldEnd();
      if (struct.op != null) {
        oprot.writeFieldBegin(OP_FIELD_DESC);
        oprot.writeI32(struct.op.getValue());
        oprot.writeFieldEnd();
      }
      if (struct.commit != null) {
        if (struct.isSetCommit()) {
          oprot.writeFieldBegin(COMMIT_FIELD_DESC);
          oprot.writeString(struct.commit);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class WorkTupleSchemeFactory implements SchemeFactory {
    public WorkTupleScheme getScheme() {
      return new WorkTupleScheme();
    }
  }

  private static class WorkTupleScheme extends TupleScheme<Work> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Work struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetNum1()) {
        optionals.set(0);
      }
      if (struct.isSetNum2()) {
        optionals.set(1);
      }
      if (struct.isSetOp()) {
        optionals.set(2);
      }
      if (struct.isSetCommit()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetNum1()) {
        oprot.writeI32(struct.num1);
      }
      if (struct.isSetNum2()) {
        oprot.writeI32(struct.num2);
      }
      if (struct.isSetOp()) {
        oprot.writeI32(struct.op.getValue());
      }
      if (struct.isSetCommit()) {
        oprot.writeString(struct.commit);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Work struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.num1 = iprot.readI32();
        struct.setNum1IsSet(true);
      }
      if (incoming.get(1)) {
        struct.num2 = iprot.readI32();
        struct.setNum2IsSet(true);
      }
      if (incoming.get(2)) {
        struct.op = Operation.findByValue(iprot.readI32());
        struct.setOpIsSet(true);
      }
      if (incoming.get(3)) {
        struct.commit = iprot.readString();
        struct.setCommitIsSet(true);
      }
    }
  }

}

